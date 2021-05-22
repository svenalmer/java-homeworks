package hr.fer.zemris.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.mat.Complex;
import hr.fer.zemris.mat.ComplexPolynomial;
import hr.fer.zemris.mat.ComplexRootedPolynomial;

public class NewtonParallel {
	
	static int workers;
	static int tracks;
	public static final double CONVERGENCE_TRESHOLD = 0.003;
	static ComplexRootedPolynomial pol;
	static ComplexPolynomial polynom;
	static ComplexPolynomial derived;
	
	
	public static void main(String[] args) {
		
		int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
		boolean gotWorkers = false;
		boolean gotTracks = false;
		
		if (args.length == 0) {
			workers = numberOfAvailableProcessors;
			tracks = 4 * numberOfAvailableProcessors;
		} else if (args[0].charAt(0) == '-' && args[0].charAt(1) == '-'){
			for (int i = 0; i < args.length; i++) {
				if (args[i].contains("workers")) {
					if (gotWorkers) throw new IllegalArgumentException("Cannot specify the same value twice!");
					workers = Integer.parseInt(args[i].split("=")[1]);
					gotWorkers = true;
				} else if (args[i].contains("tracks")) {
					if (gotTracks) throw new IllegalArgumentException("Cannot specify the same value twice!");
					tracks = Integer.parseInt(args[i].split("=")[1]);
					gotTracks = true;
				} else throw new IllegalArgumentException("Illegal argument format!");
			}
		} else if (args[0].charAt(0) == '-') {
			for (int i = 0; i < args.length; i += 2) {
				if (args[i].equals("-w")) {
					if (gotWorkers) throw new IllegalArgumentException("Cannot specify the same value twice!");
					workers = Integer.parseInt(args[i + 1]);
					gotWorkers = true;
				} else if (args[i].equals("-t")) {
					if (gotTracks) throw new IllegalArgumentException("Cannot specify the same value twice!");
					tracks = Integer.parseInt(args[i + 1]);
					gotTracks = true;
				} else throw new IllegalArgumentException("Illegal argument format!");
			}
		} else throw new IllegalArgumentException("Illegal argument format!");
		
		if (!gotWorkers) workers = numberOfAvailableProcessors;
		if (!gotTracks) tracks = 4 * numberOfAvailableProcessors;
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		int i = 1;
		List<Complex> roots = new ArrayList<>();
		while (true) {
			System.out.print("Root " + i + ">");
			i++;
			
			String root = sc.nextLine();
			root = root.replaceAll("\\s+", "");
			if (root.equals("done")) break;
			
			roots.add(Complex.parse(root));
		}
		sc.close();
		Complex[] rootArr = new Complex[roots.size()];
		for(int j = 0; j < roots.size(); j++) {
			rootArr[j] = roots.get(j);
		}
		
		pol = new ComplexRootedPolynomial(new Complex(1, 0), rootArr);
		polynom = pol.toComplexPolynom();
		derived = polynom.derive();
		
		FractalViewer.show(new MojProducer());
	}
	
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static final PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
//			Mandelbrot.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
			
			calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
		}
	}
	
	/**
	 * Calculates the needed values for a portion of data array.
	 * It generates data for a horizontal track of the picture that is determined by yMin and yMax.  
	 *
	 * @param reMin the minimum real value being tested
	 * @param reMax the maximum real value being tested
	 * @param imMin the minimum imaginary value being tested
	 * @param imMax the maximum imaginary value being tested
	 * @param width the width of the picture
	 * @param height the height of the picture
	 * @param m the maximum number of iterations
	 * @param yMin the minimum y value of the picture track
	 * @param yMax the maximum y value of the picture track
	 * @param data the data array
	 * @param cancel the cancel variable
	 */
	private static void calculate(double reMin, double  reMax, double imMin, double imMax,
							int width, int height, int m, int yMin, int yMax, short[] data, AtomicBoolean cancel) {
		
		int offset = yMin * width;
		for (int y = yMin; y <= yMax; y++) {
			if(cancel.get()) break;
			for (int x = 0; x < width; x++) {
				int iters = 0;
				double zre = x / (width-1.0) * (reMax - reMin) + reMin;
				double zim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
				Complex zn = new Complex(zre, zim);
				Complex znold;
				do {
					iters++;
					Complex numerator = polynom.apply(zn); 
					Complex denominator = derived.apply(zn);
					znold = zn;
					Complex fraction = numerator.div(denominator); 
					zn = zn.sub(fraction);
				} while (znold.distanceFrom(zn) > CONVERGENCE_TRESHOLD && iters < m);
				int index = pol.indexOfClosestRootFor(zn, CONVERGENCE_TRESHOLD);
				data[offset++] = (short) (index + 1);
			}
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			if (tracks > height) tracks = height;
			int brojYPoTraci = height / tracks;
			System.out.println("Broj radnika:" + workers + "\nBroj traka:" + tracks);
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[workers];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < tracks; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==tracks-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			observer.acceptResult(data, (short)(polynom.order() + 1), requestNo);
		}
	}

	
}
