package GUI;

import Graph.Graph;
import TrickyTriangles.TrickyTriangles;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author s115869
 */
public class GUI {

    TrickyTriangles main;
    
    JFrame frame;
    Container pane;
    
    Boolean layoutHorizontal;
    
    Panel workCanvas;
    Panel goalCanvas;
    
    JButton newGame;
    
    JButton resetGame;
    
    JLabel modeLabel;
    JComboBox modeSelect;
    int modeSelectedIndex;
    
    JLabel nPointsLabel;
    JComboBox nPointsSelect;
    int nPointsSelectedIndex;
    boolean edgeAffChecked;
    
    JLabel edgeAffLabel;
    JCheckBox edgeAffCheck;

    // For debugging purposes
    public GUI(Graph graph){
        frame = new JFrame();
        frame.setTitle("Tricky Triangles");
        frame.setSize(800, 500);
        frame.setMinimumSize(new Dimension(420, 200));
        layoutHorizontal = frame.getWidth() > frame.getHeight();
        frame.setLocation(20, 20);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setBackground( Color.BLACK );
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e)
            {
                if (frame.getWidth() > frame.getHeight() ^ layoutHorizontal)
                {
                    super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                    layoutHorizontal = frame.getWidth() > 1.1 * frame.getHeight();
                    makeLayout();
                }
            }
        });

        /*
         * Canvas
         */
        workCanvas = new Panel(graph, main.getGoalGraph(), frame, main);
        frame.add(workCanvas);

        frame.setVisible(true);
    }
    
    public void newGame(){
        main.newGame();
        frame.remove(workCanvas);
        frame.remove(goalCanvas);
        workCanvas = new Panel(main.getWorkGraph(), main.getGoalGraph(), frame, main);
        goalCanvas = new Panel(main.getGoalGraph(), main.getGoalGraph(), frame, main);
        makeLayout();
    }
    
    public GUI(TrickyTriangles main) {
        /*
         * Window
         */
        this.main = main;
        frame = new JFrame();
        frame.setTitle("Tricky Triangles");
        frame.setSize(800, 500);
        frame.setMinimumSize(new Dimension(420, 200));
        layoutHorizontal = frame.getWidth() > frame.getHeight();
        frame.setLocation(20, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setBackground( Color.BLACK );
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e)
            {
                if (frame.getWidth() > frame.getHeight() ^ layoutHorizontal)
                {
                    super.componentResized(e); //To change body of generated methods, choose Tools | Templates.
                    layoutHorizontal = frame.getWidth() > 1.1 * frame.getHeight();
                    makeLayout();
                }
            }
        });

        /*
         * Canvas
         */
        workCanvas = new Panel(main.getWorkGraph(), main.getGoalGraph(), frame, main);
        goalCanvas = new Panel(main.getGoalGraph(), main.getGoalGraph(), frame, main);

        /*
         * "New game" button
         */
        newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                newGame();
            }
        });

        /*
         * "Reset game" button
         */
        resetGame = new JButton();
        resetGame.setText("Reset current game");
        resetGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                main.resetGame();
                frame.remove(workCanvas);
                workCanvas = new Panel(main.getWorkGraph(), main.getGoalGraph(), frame, main);
                makeLayout();
            }
        });
        
        /*
         * Mode selection
         */
        modeLabel = new JLabel("Mode:");
        HashMap<String,TrickyTriangles.Mode> labelsToModes = new HashMap<String,TrickyTriangles.Mode>();
        labelsToModes.put("Move To Target", TrickyTriangles.Mode.MOVE_TO_TARGET);
        labelsToModes.put("Delaunay Game", TrickyTriangles.Mode.DELAUNAY_GAME);
        String modeLabels[] = {"Move To Target", "Delaunay Game"};
        modeSelect = new JComboBox(modeLabels);
        modeSelectedIndex = 0;
        modeSelect.setSelectedIndex(modeSelectedIndex);
        modeSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                String[] options = {"OK", "Cancel"};
                if(main.getWorkGraph().equals(main.getWorkGraphInitial())
                        || JOptionPane.showOptionDialog(
                        frame, 
                        "Do you want to start a new game with the new settings?", 
                        "Change game settings", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, options, null) == 0)
                {
                    modeSelectedIndex = modeSelect.getSelectedIndex();
                    main.setMode(labelsToModes.get(modeLabels[modeSelectedIndex]));
                    newGame();
                }
                else
                {
                    modeSelect.setSelectedIndex(modeSelectedIndex);
                }
            }
        });
        
        /*
         * Number of points selection
         */
        nPointsLabel = new JLabel("Number of points:");
        int nPointsOptions[] = {5, 10, 15, 25, 60};
        String nPointsLabels[] = new String[nPointsOptions.length];
        for (int i = 0; i < nPointsOptions.length; i++)
        {
            nPointsLabels[i] = ((Integer) nPointsOptions[i]).toString();
        }
        nPointsSelect = new JComboBox(nPointsLabels);
        nPointsSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                String[] options = {"OK", "Cancel"};
                if(main.getWorkGraph().equals(main.getWorkGraphInitial())
                        || JOptionPane.showOptionDialog(
                        frame, 
                        "Do you want to start a new game with the new settings?", 
                        "Change game settings", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, options, null) == 0)
                {
                    nPointsSelectedIndex = nPointsSelect.getSelectedIndex();
                    main.setNoPoints(nPointsOptions[nPointsSelectedIndex]);
                    newGame();
                }
                else
                {
                    nPointsSelect.setSelectedIndex(nPointsSelectedIndex);
                }
            }
        });
        
        /*
         * Edge affirmation check button
         */
        edgeAffLabel = new JLabel("Edge affirmation:");
        edgeAffCheck = new JCheckBox();
        edgeAffCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                String[] options = {"OK", "Cancel"};
                if(main.getWorkGraph().equals(main.getWorkGraphInitial())
                        || JOptionPane.showOptionDialog(
                        frame, 
                        "Do you want to start a new game with the new settings?", 
                        "Change game settings", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, options, null) == 0)
                {
                    edgeAffChecked = edgeAffCheck.isSelected();
                    main.setEdgeAff(edgeAffChecked);
                    newGame();
                }
                else
                {
                    edgeAffCheck.setSelected(edgeAffChecked);
                }
            }
        });
        
        /*
         * Make layout
         */
        makeLayout();

        frame.setVisible(true);
    }

    private void makeLayout() {
        pane = frame.getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        //pane.setBackground(Color.WHITE);
        gl.setAutoCreateContainerGaps(true);
        
        if (layoutHorizontal)
        {
            if (main.getMode() == TrickyTriangles.Mode.MOVE_TO_TARGET)
            {
                gl.setHorizontalGroup(gl.createParallelGroup()
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(workCanvas)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(goalCanvas)
                        )
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(newGame)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetGame)
                                .addGap(0, frame.getWidth()*3, frame.getWidth()*3)
                                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(modeLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addComponent(nPointsLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(edgeAffLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                                .addContainerGap(0, 0)
                        )
                );
                gl.setVerticalGroup(gl.createSequentialGroup()
                        .addGroup(gl.createParallelGroup()
                                .addComponent(workCanvas)
                                .addComponent(goalCanvas)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(newGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addComponent(resetGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addGroup(gl.createSequentialGroup()
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(modeLabel)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(nPointsLabel)
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(edgeAffLabel)
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                        )
                );
            }
            else
            {
                gl.setHorizontalGroup(gl.createParallelGroup()
                        .addComponent(workCanvas)
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(newGame)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetGame)
                                .addGap(0, frame.getWidth()*3, frame.getWidth()*3)
                                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(modeLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addComponent(nPointsLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(edgeAffLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                                .addContainerGap(0, 0)
                        )
                );
                gl.setVerticalGroup(gl.createSequentialGroup()
                        .addComponent(workCanvas)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(newGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addComponent(resetGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addGroup(gl.createSequentialGroup()
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(modeLabel)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(nPointsLabel)
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(edgeAffLabel)
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                        )
                );
            }
        }
        else
        {
            if (main.getMode() == TrickyTriangles.Mode.MOVE_TO_TARGET)
            {
                gl.setHorizontalGroup(gl.createParallelGroup()
                        .addGroup(gl.createParallelGroup()
                                .addComponent(workCanvas)
                                .addComponent(goalCanvas)
                        )
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(newGame)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetGame)
                                .addGap(0, frame.getWidth()*3, frame.getWidth()*3)
                                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(modeLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addComponent(nPointsLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(edgeAffLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                                .addContainerGap(0, 0)
                        )
                );
                gl.setVerticalGroup(gl.createSequentialGroup()
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(workCanvas)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(goalCanvas)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(newGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addComponent(resetGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addGroup(gl.createSequentialGroup()
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(modeLabel)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(nPointsLabel)
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(edgeAffLabel)
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                        )
                );
            }
            else
            {
                gl.setHorizontalGroup(gl.createParallelGroup()
                        .addComponent(workCanvas)
                        .addGroup(gl.createSequentialGroup()
                                .addComponent(newGame)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetGame)
                                .addGap(0, frame.getWidth()*3, frame.getWidth()*3)
                                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(modeLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addComponent(nPointsLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createSequentialGroup()
                                                .addContainerGap(0, 0)
                                                .addComponent(edgeAffLabel)
                                                .addGap(0, 0, frame.getWidth())
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                                .addContainerGap(0, 0)
                        )
                );
                gl.setVerticalGroup(gl.createSequentialGroup()
                        .addComponent(workCanvas)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(newGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addComponent(resetGame, GroupLayout.Alignment.CENTER, gl.PREFERRED_SIZE, 40, 40)
                                .addGroup(gl.createSequentialGroup()
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(modeLabel)
                                                .addComponent(modeSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(nPointsLabel)
                                                .addComponent(nPointsSelect, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE, gl.PREFERRED_SIZE)
                                        )
                                        .addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(edgeAffLabel)
                                                .addComponent(edgeAffCheck)
                                        )
                                )
                        )
                );
            }
        }
    }

    public void show(Graph g) {
        workCanvas.set(g);
        workCanvas.repaint();
        goalCanvas.set(g);
        goalCanvas.repaint();
    }

}
