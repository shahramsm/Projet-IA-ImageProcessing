package imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Population {

	public static final int maxInd = 100;
	public ArrayList<Individu> Images = new ArrayList<Individu>();

	public Population() {

		for (int i = 0; i < maxInd; i++) { // creation de la population
			Individu ind = new Individu(); // creation des individus
			Images.add(ind); // liste des images avec leur image et liste de polygone et leur fitnesse
		}
	}

	// methode de mutation
	static List<ConvexPolygon> mutate(Individu image) {
		Random random = new Random();
		int location = random.nextInt(image.ls.size());
		int location2 = random.nextInt(image.ls.size());
		int m =random.nextInt(1);
		int d = random.nextInt(9 - 3 +1) +3 ;
		
		if(m==0){
			ConvexPolygon poly = new ConvexPolygon(d);

			image.ls.set(location, poly);

		}
		else{
			ConvexPolygon poly = new ConvexPolygon(d);
			((ConvexPolygon) poly).setcouleur();
			image.ls.set(location, poly);
			
		}
		return image.ls;

	}
	
	// methode de croisement 1
	public Individu Croisement(Individu ind1, Individu ind2) {
		ArrayList<ConvexPolygon> ld = new ArrayList<ConvexPolygon>();
		ArrayList<ConvexPolygon> ld1 = new ArrayList<ConvexPolygon>();
		Random rand = new Random();
		int s = rand.nextInt(ind1.ls.size() - 1);

		for (int i = 0; i < s; i++) {
			ld.add(ind1.ls.get(i));
			ld1.add(ind2.ls.get(i));
		}
		for (int i = s; i < ind2.ls.size(); i++) {
			ld.add(ind2.ls.get(i));
			ld1.add(ind1.ls.get(i));
		}
		Individu ind3 = new Individu(ld);
        Individu ind4 = new Individu(ld1);

		if (ind3.fitness > ind4.fitness) {
			return ind4;
		}

		else
			return ind3;

	}
	
	// methode de croisement 2
	public Individu Croisement2(Individu ind1, Individu ind2) {
		ArrayList<ConvexPolygon> ld = new ArrayList<ConvexPolygon>();
		ArrayList<ConvexPolygon> ld1 = new ArrayList<ConvexPolygon>();
		Random rand = new Random();
		int s = rand.nextInt(ind1.ls.size());
		int m = rand.nextInt(ind1.ls.size());
		int h;
		if (s > m) {h = m;m = s;s = h;}
		for (int i = 0; i < s; i++) {
			ld.add(ind1.ls.get(i));
			ld1.add(ind2.ls.get(i));
		}

		for (int i = s; i < m; i++) {
			ld.add(ind2.ls.get(i));
			ld1.add(ind1.ls.get(i));
		}
		for (int i = m; i < ind1.ls.size(); i++) {
			ld.add(ind1.ls.get(i));
			ld1.add(ind2.ls.get(i));
		}
		Individu ind3 = new Individu(ld);
		Individu ind4 = new Individu(ld1);

		if (ind3.fitness > ind4.fitness) {
			return ind4;
		}

		else
			return ind3;

	}
	
	// methode de croisement 3
	public Individu Croisement3(Individu ind1, Individu ind2) {
		ArrayList<ConvexPolygon> ld = new ArrayList<ConvexPolygon>();
		ArrayList<ConvexPolygon> ld1 = new ArrayList<ConvexPolygon>();
		Random rand = new Random();
		int n=0;
		int m=1;
		while(n<ind2.ls.size()) {
			ld.add(ind1.ls.get(n));
			ld1.add(ind2.ls.get(n));
			n++;
		}
		while(m<ind2.ls.size()) {
			ld.add(ind1.ls.get(m));
			ld1.add(ind2.ls.get(m));
			m++;
			
		}
		Individu ind3 = new Individu(ld);
		Individu ind4 = new Individu(ld1);

		if (ind3.fitness > ind4.fitness) {
			return ind4;
		}

		else
			return ind3;

			
			
		
		
		
	}
		
		
		
		

	public ArrayList<Individu> tri(ArrayList<Individu> list) {

		Collections.sort(list);
		return list;
	}

}
