package imageProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Individu implements Comparable<Individu>,Cloneable {
	public Group image;
	 public double fitness; 
	 List<ConvexPolygon> ls; // la liste des polygones d'une image 
	// creation d'un individu
	public Individu() {
		ls = new ArrayList<ConvexPolygon>(); 
		Random a= new Random();
		int b= a.nextInt(9-3+1)+3;
	       for (int j = 0; j <50; j++) {
	    	   
			ls.add(new ConvexPolygon(b));}
			
			Group image = new Group();
			Rectangle fond = new Rectangle();
			fond.setWidth(ConvexPolygon.max_X);
			fond.setHeight(ConvexPolygon.max_Y);
			fond.setFill(Color.BLACK);
			image.getChildren().add(fond);
			
			for (ConvexPolygon p : ls) {
				image.getChildren().add(p);}
			
			 WritableImage wimg = new WritableImage(ConvexPolygon.max_X,ConvexPolygon.max_Y);
				image.snapshot(null,wimg);
				PixelReader pr = wimg.getPixelReader();
			    double res=0;
				for (int i=0;i<ConvexPolygon.max_X;i++){
					for (int j=0;j<ConvexPolygon.max_Y;j++){
						Color c = pr.getColor(i, j);
						res += Math.pow(c.getBlue()-Test.target[i][j].getBlue(),2)
						+Math.pow(c.getRed()-Test.target[i][j].getRed(),2)
						+Math.pow(c.getGreen()-Test.target[i][j].getGreen(),2);
						
					}
				}
			  fitness= Math.sqrt(res);
			
	}
	// creation d'un individu
	public Individu(List<ConvexPolygon> ls ) {
		
		this.ls=ls;
		Group image = new Group();
		Rectangle fond = new Rectangle();
		fond.setWidth(ConvexPolygon.max_X);
		fond.setHeight(ConvexPolygon.max_Y);
		fond.setFill(Color.BLACK);
		image.getChildren().add(fond);
		for (ConvexPolygon p : ls)
			try {
				image.getChildren().add(p); 
			}catch(Exception e) {}
		WritableImage wimg = new WritableImage(ConvexPolygon.max_X,ConvexPolygon.max_Y);
		image.snapshot(null,wimg);
		PixelReader pr = wimg.getPixelReader();
	    double res=0;
		for (int i=0;i<ConvexPolygon.max_X;i++){
			for (int j=0;j<ConvexPolygon.max_Y;j++){
				Color c = pr.getColor(i, j);
				res += Math.pow(c.getBlue()-Test.target[i][j].getBlue(),2)
				+Math.pow(c.getRed()-Test.target[i][j].getRed(),2)
				+Math.pow(c.getGreen()-Test.target[i][j].getGreen(),2);
				
			}
		}
	  fitness= Math.sqrt(res);
		
	}
	// methode utiliser pour la methode de tri 
	@Override
	public int compareTo(Individu arg) {
		if(fitness==arg.fitness) return 0;
		if(fitness>arg.fitness) return 1;
		 return -1;	
	}
		
	}


