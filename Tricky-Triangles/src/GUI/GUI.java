package GUI;

import Graph.Graph;
import TrickyTriangles.TrickyTriangles;
import java.awt.Container;
import java.awt.LayoutManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author s115869
 */
public class GUI {

    Panel workCanvas;
    Panel goalCanvas;
    JFrame frame;

    public GUI(TrickyTriangles main) {
        /*
         * Window
         */
        frame = new JFrame();
        frame.setTitle("Tricky Triangles");
        frame.setSize(800, 500);
        frame.setLocation(20, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setBackground( Color.BLACK );

        Container pane = frame.getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        /*
         * Canvas
         */
        workCanvas = new Panel(main.getWorkGraph());
        goalCanvas = new Panel(main.getGoalGraph());

        /*
         * "New game" button
         */
        JButton newGame = new JButton("New game");

        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(newGame));

        /*
         * "Reset game" button
         */
        JButton resetGame = new JButton();
        resetGame.setText("Reset game");

        /*
         * Make layout
         */
//        gl.setHorizontalGroup(
//                gl.createSequentialGroup()
//                .addComponent(newGame)
//                .addComponent(resetGame)
//        );
//        gl.setVerticalGroup(
//                gl.createParallelGroup()
//                .addComponent(newGame)
//                .addComponent(resetGame)
//        );
        gl.setHorizontalGroup(
                gl.createParallelGroup()
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(workCanvas)
                                .addComponent(goalCanvas)
                        )
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(newGame)
                                .addComponent(resetGame)
                        )
        );
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGroup(gl.createParallelGroup()
                                .addComponent(workCanvas)
                                .addComponent(goalCanvas)
                        )
                        .addGroup(gl.createParallelGroup()
                                .addComponent(newGame)
                                .addComponent(resetGame)
                        )
        );
        frame.setVisible(true);
    }

    public void show(Graph g) {
        workCanvas.set(g);
        workCanvas.repaint();
        goalCanvas.set(g);
        goalCanvas.repaint();
    }

}
