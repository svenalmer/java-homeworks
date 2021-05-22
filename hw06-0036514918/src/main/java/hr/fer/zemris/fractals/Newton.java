package hr.fer.zemris.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.mat.Complex;
import hr.fer.zemris.mat.ComplexPolynomial;
import hr.fer.zemris.mat.ComplexRootedPolynomial;

public class Newton {
	
	public static final double CONVERGENCE_TRESHOLD = 0.003;
	static ComplexRootedPolynomial pol;
	static ComplexPolynomial polynom;
	
	public static void main(String[] args) {
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
		FractalViewer.show(new MojProducer());
	}
	
	public static class MojProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Image of fractal will appear shortly. Thank you");
			ComplexPolynomial derived = polynom.derive();
			short[] data = new short[width * height];
			int m = 16*16*16;
			int offset = 0;
			for (int y = 0; y < height; y++) {
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
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynom.order() + 1), requestNo);
		}
		
	}
}
