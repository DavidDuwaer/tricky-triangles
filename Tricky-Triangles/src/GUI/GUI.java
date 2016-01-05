package GUI;

import Graph.Graph;
import TrickyTriangles.TrickyTriangles;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JButton newGame;
    JButton resetGame;
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
        newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                main.newGame();
                frame.remove(workCanvas);
                frame.remove(goalCanvas);
                workCanvas = new Panel(main.getWorkGraph());
                goalCanvas = new Panel(main.getGoalGraph());
                makeLayout(gl);
            }
        });

        /*
         * "Reset game" button
         */
        resetGame = new JButton();
        resetGame.setText("Reset game");
        resetGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                main.resetGame();
                frame.remove(workCanvas);
                workCanvas = new Panel(main.getWorkGraph());
                makeLayout(gl);
            }
        });

        /*
         * Make layout
         */
        makeLayout(gl);

        frame.setVisible(true);
    }

    private void makeLayout(GroupLayout gl) {
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
    }

    public void show(Graph g) {
        workCanvas.set(g);
        workCanvas.repaint();
        goalCanvas.set(g);
        goalCanvas.repaint();
    }

}
