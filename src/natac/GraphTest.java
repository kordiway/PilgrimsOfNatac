package natac;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;

/**
 * creates hexagonal pattern with graph vertices given from txt file just to
 * create base outline for board at later stage to have proof of concept with
 * proper edges for each vertex
 *
 * @author Korbin Ordiway
 */
public class GraphTest {

	public static void main(String[] args) {
		Graph graph = new Graph(new In("src/natac/resources/graph.txt"));

		double[][] coords = new double[54][2];

		coords[0] = new double[] { 0.3200, 0.9500 };
		coords[1] = new double[] { 0.5000, 0.9500 };
		coords[2] = new double[] { 0.6800, 0.9500 };
		coords[3] = new double[] { 0.2300, 0.8938 };
		coords[4] = new double[] { 0.4100, 0.8938 };
		coords[5] = new double[] { 0.5900, 0.8938 };
		coords[6] = new double[] { 0.7700, 0.8938 };
		coords[7] = new double[] { 0.2300, 0.7812 };
		coords[8] = new double[] { 0.4100, 0.7812 };
		coords[9] = new double[] { 0.5900, 0.7812 };
		coords[10] = new double[] { 0.7700, 0.7812 };
		coords[11] = new double[] { 0.1400, 0.7250 };
		coords[12] = new double[] { 0.3200, 0.7250 };
		coords[13] = new double[] { 0.5000, 0.7250 };
		coords[14] = new double[] { 0.6800, 0.7250 };
		coords[15] = new double[] { 0.8600, 0.7250 };
		coords[16] = new double[] { 0.1400, 0.6125 };
		coords[17] = new double[] { 0.3200, 0.6125 };
		coords[18] = new double[] { 0.5000, 0.6125 };
		coords[19] = new double[] { 0.6800, 0.6125 };
		coords[20] = new double[] { 0.8600, 0.6125 };
		coords[21] = new double[] { 0.0500, 0.5563 };
		coords[22] = new double[] { 0.2300, 0.5563 };
		coords[23] = new double[] { 0.4100, 0.5563 };
		coords[24] = new double[] { 0.5900, 0.5563 };
		coords[25] = new double[] { 0.7700, 0.5563 };
		coords[26] = new double[] { 0.9500, 0.5563 };
		coords[27] = new double[] { 0.0500, 0.4437 };
		coords[28] = new double[] { 0.2300, 0.4437 };
		coords[29] = new double[] { 0.4100, 0.4437 };
		coords[30] = new double[] { 0.5900, 0.4437 };
		coords[31] = new double[] { 0.7700, 0.4437 };
		coords[32] = new double[] { 0.9500, 0.4437 };
		coords[33] = new double[] { 0.1400, 0.3875 };
		coords[34] = new double[] { 0.3200, 0.3875 };
		coords[35] = new double[] { 0.5000, 0.3875 };
		coords[36] = new double[] { 0.6800, 0.3875 };
		coords[37] = new double[] { 0.8600, 0.3875 };
		coords[38] = new double[] { 0.1400, 0.2750 };
		coords[39] = new double[] { 0.3200, 0.2750 };
		coords[40] = new double[] { 0.5000, 0.2750 };
		coords[41] = new double[] { 0.6800, 0.2750 };
		coords[42] = new double[] { 0.8600, 0.2750 };
		coords[43] = new double[] { 0.2300, 0.2187 };
		coords[44] = new double[] { 0.4100, 0.2187 };
		coords[45] = new double[] { 0.5900, 0.2187 };
		coords[46] = new double[] { 0.7700, 0.2187 };
		coords[47] = new double[] { 0.2300, 0.1062 };
		coords[48] = new double[] { 0.4100, 0.1062 };
		coords[49] = new double[] { 0.5900, 0.1062 };
		coords[50] = new double[] { 0.7700, 0.1062 };
		coords[51] = new double[] { 0.3200, 0.0500 };
		coords[52] = new double[] { 0.5000, 0.0500 };
		coords[53] = new double[] { 0.6800, 0.0500 };

		StdDraw.setCanvasSize(800, 800);
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);

		// Draw edges
		StdDraw.setPenColor(Color.DARK_GRAY);
		StdDraw.setPenRadius(0.005);
		for (int v = 0; v < 54; v++) {
			for (int w : graph.adj(v)) {
				if (w > v) {
					StdDraw.line(coords[v][0], coords[v][1], coords[w][0], coords[w][1]);
				}
			}
		}

		// Draw vertices
		for (int i = 0; i < 54; i++) {
			StdDraw.setPenColor(Color.RED);
			StdDraw.filledCircle(coords[i][0], coords[i][1], 0.018);
			StdDraw.setPenColor(Color.WHITE);
			StdDraw.text(coords[i][0], coords[i][1], String.valueOf(i));
		}

	}
}