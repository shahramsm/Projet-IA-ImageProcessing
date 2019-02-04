package imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sun.javafx.iio.ImageStorage;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {
	static Color[][] target;
	Group image;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage myStage) {
		String targetImage = "monaLisa-100.jpg";

		// Color[][] target = null;
		int maxX = 0;
		int maxY = 0;
		try {
			BufferedImage bi = ImageIO.read(new File(targetImage));
			maxX = bi.getWidth();
			maxY = bi.getHeight();
			ConvexPolygon.max_X = maxX;
			ConvexPolygon.max_Y = maxY;
			target = new Color[maxX][maxY];
			for (int i = 0; i < maxX; i++) {
				for (int j = 0; j < maxY; j++) {
					int argb = bi.getRGB(i, j);
					int b = (argb) & 0xFF;
					int g = (argb >> 8) & 0xFF;
					int r = (argb >> 16) & 0xFF;
					int a = (argb >> 24) & 0xFF;
					target[i][j] = Color.rgb(r, g, b);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
			System.exit(9);
		}
		System.out.println("Read target image " + targetImage + " " + maxX + "x" + maxY);
		//creation de la population
		Population Pop = new Population();
		// creation d'une liste des individus trier
		ArrayList<Individu> listri = new ArrayList<Individu>(); // creation
																// d'une liste
		listri = Pop.tri(Pop.Images);
		int gen = 0;
		double m = Pop.Images.get(0).fitness;
		int n = 0;
		for (int compteur = 0; compteur < 20000; compteur++) {

			// si la fitness stabilise au bout de 25 generation on garde les 30
			// meilleurs et on genere 70 nouveau
			if (n == 25) {
				for (int l = 30; l < listri.size() - l; l++) {
					Individu in = new Individu();
					listri.set(l, in);
				}

			}
			listri = Pop.tri(Pop.Images); // trier la liste
			
			// croisement des individu et creation de nouvelle generation
			for (int f = 0; f < 2; f++) {
				int cmp = 30;
				for (int i = 0; i < listri.size() - 1; i++) {

					Random ran = new Random();
					int d = ran.nextInt(30);
					int s = ran.nextInt(30);
					Individu fils = Pop.Croisement(listri.get(s), listri.get(d));
					Individu fils1 = Pop.Croisement2(listri.get(s), listri.get(d));
					listri.set(((i + cmp) % listri.size()), fils);
					listri.set(listri.size() - 1, fils1);

					Pop.Images = listri;
					listri = Pop.tri(Pop.Images);
				}

				// mutation des individu et choisir les meilleurs
				for (int j = 0; j < listri.size(); j++) {
					Individu tmp = listri.get(j);
					Individu last = listri.get(listri.size() - 1);

					List<ConvexPolygon> ls = Population.mutate(listri.get(j));
					Individu tmp1 = new Individu(ls);

					if (tmp1.fitness < tmp.fitness) {
						listri.set(j, tmp1);

					} else if (tmp1.fitness < last.fitness) {
						listri.set(listri.size() - 1, tmp1);
					}
					Pop.Images = listri;
					listri = Pop.tri(Pop.Images);
				}

				if (m == listri.get(0).fitness)
					n++;
				else {
					n = 0;
					m = Pop.Images.get(0).fitness;
				}
			}
			System.out.println(Pop.Images.get(0).fitness + "  generation " + compteur); // affichage
																						// de
																						// la
																						// fitnesse
																						// de
																						// la meilleure image
			

			// affichage de photo a chaque 50 générations

			if ((compteur % 50) == 0) {

				Group image = new Group();
				Rectangle fond = new Rectangle();
				fond.setWidth(ConvexPolygon.max_X);
				fond.setHeight(ConvexPolygon.max_Y);
				fond.setFill(Color.BLACK);
				image.getChildren().add(fond);
				List<ConvexPolygon> ld = Pop.Images.get(0).ls;
				for (ConvexPolygon p : ld) {
					image.getChildren().add(p);
				}

				WritableImage wimg = new WritableImage(ConvexPolygon.max_X, ConvexPolygon.max_Y);
				image.snapshot(null, wimg);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(wimg, null);
				try {
					ImageIO.write(renderedImage, "png",
							new File("test-" + compteur + " " + Pop.Images.get(0).fitness + ".png"));
					System.out.println("wrote image in " + "test.png");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Scene scene = new Scene(image, ConvexPolygon.max_X, ConvexPolygon.max_Y);
				myStage.setScene(scene);
				myStage.show();
			}

		}
	}
}
