import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PaintBrush extends Applet {
    private Color currentColor = Color.BLACK;
    private String currentShape = "Freehand";
    private boolean filled = false;
    private ArrayList<Shapes> shapes = new ArrayList<>();
    private Shapes currentShapes = null;
    private Button redButton, greenButton, blueButton, rectangleButton, ovalButton, lineButton, freeHandButton, eraserButton, clearAllButton;
    private Checkbox filledCheckbox;

    @Override
    public void init() {
        try {
            redButton = new Button("Red");
            greenButton = new Button("Green");
            blueButton = new Button("Blue");

            redButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentColor = Color.RED;
                }
            });

            greenButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentColor = Color.GREEN;
                }
            });

            blueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentColor = Color.BLUE;
                }
            });

            rectangleButton = new Button("Rectangle");
            ovalButton = new Button("Oval");
            lineButton = new Button("Line");
            freeHandButton = new Button("Free Hand");
            eraserButton = new Button("Eraser");

            rectangleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentShape = "Rectangle";
                }
            });

            ovalButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentShape = "Oval";
                }
            });

            lineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentShape = "Line";
                }
            });

            freeHandButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentShape = "Freehand";
                }
            });

            eraserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        currentShape = "Eraser";
                }
            });

            clearAllButton = new Button("Clear All");
            clearAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        shapes.clear();
                        repaint();
                }
            });

            filledCheckbox = new Checkbox("Filled", filled);
            filledCheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                        filled = filledCheckbox.getState();
                }
            });
			redButton.setBackground(Color.RED);
            add(redButton);
			greenButton.setBackground(Color.GREEN);
            add(greenButton);
			blueButton.setBackground(Color.BLUE);
            add(blueButton);
            add(rectangleButton);
            add(ovalButton);
            add(lineButton);
            add(freeHandButton);
            add(eraserButton);
            add(clearAllButton);
            add(filledCheckbox);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        switch (currentShape) {
                            case "Rectangle":
                                currentShapes = new RectangleShape(e.getPoint(), currentColor, filled);
                                break;
                            case "Oval":
                                currentShapes = new OvalShape(e.getPoint(), currentColor, filled);
                                break;
                            case "Line":
                                currentShapes = new LineShape(e.getPoint(), currentColor);
                                break;
                            case "Freehand":
                                currentShapes = new FreehandShape(currentColor);
                                break;
                            case "Eraser":
                                currentShapes = new Eraser();
                                break;
                        }
                        if (currentShapes != null) {
                            shapes.add(currentShapes);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error starting drawing shape: " + ex.getMessage());
                    }
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    try {
                         if (currentShapes != null) {
                            currentShapes.update(e.getPoint());
                            repaint();
                        } 
                    } catch (Exception ex) {
                        System.out.println("Error dragging shape: " + ex.getMessage());
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    try {
                        currentShapes = null;
                    } catch (Exception ex) {
                        System.out.println("Error releasing mouse: " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println("Error initializing the applet: " + ex.getMessage());
        }
    }

    @Override
    public void paint(Graphics g) {
        try {
            for (Shapes shape : shapes) {
                shape.draw(g);
            }
        } catch (Exception ex) {
            System.out.println("Error painting shapes: " + ex.getMessage());
        }
    }

    interface Shapes {
        void draw(Graphics g);
        void update(Point p);
    }

    class RectangleShape implements Shapes {
        private Point start, end;
        private Color color;
        private boolean filled;

        public RectangleShape(Point start, Color color, boolean filled) {
            this.start = start;
            this.color = color;
            this.filled = filled;
        }

        @Override
        public void draw(Graphics g) {
            try {
                g.setColor(color);
                int x = Math.min(start.x, end.x);
                int y = Math.min(start.y, end.y);
                int width = Math.abs(end.x - start.x);
                int height = Math.abs(end.y - start.y);
                if (filled) {
                    g.fillRect(x, y, width, height);
                } else {
                    g.drawRect(x, y, width, height);
                }
            } catch (Exception ex) {
                System.out.println("Error drawing rectangle: " + ex.getMessage());
            }
        }

        @Override
        public void update(Point p) {
            this.end = p;
        }
    }

    class OvalShape implements Shapes {
        private Point start, end;
        private Color color;
        private boolean filled;

        public OvalShape(Point start, Color color, boolean filled) {
            this.start = start;
            this.color = color;
            this.filled = filled;
        }

        @Override
        public void draw(Graphics g) {
            try {
                g.setColor(color);
                int x = Math.min(start.x, end.x);
                int y = Math.min(start.y, end.y);
                int width = Math.abs(end.x - start.x);
                int height = Math.abs(end.y - start.y);
                if (filled) {
                    g.fillOval(x, y, width, height);
                } else {
                    g.drawOval(x, y, width, height);
                }
            } catch (Exception ex) {
                System.out.println("Error drawing oval: " + ex.getMessage());
            }
        }

        @Override
        public void update(Point p) {
            this.end = p;
        }
    }

    class LineShape implements Shapes {
        private Point start, end;
        private Color color;

        public LineShape(Point start, Color color) {
            this.start = start;
			this.end = start; 
            this.color = color;
        }

        @Override
        public void draw(Graphics g) {
            try {
                g.setColor(color);
                g.drawLine(start.x, start.y, end.x, end.y);
            } catch (Exception ex) {
                System.out.println("Error drawing line: " + ex.getMessage());
            }
        }

        @Override
        public void update(Point p) {
            this.end = p;
        }
    }

    class FreehandShape implements Shapes {
        private ArrayList<Point> points = new ArrayList<>();
        private Color color;

        public FreehandShape(Color color) {
            this.color = color;
        }

        @Override
        public void draw(Graphics g) {
            try {
                g.setColor(color);
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            } catch (Exception ex) {
                System.out.println("Error drawing freehand: " + ex.getMessage());
            }
        }

        @Override
        public void update(Point p) {
            points.add(p);
        }
    }

    class Eraser implements Shapes {
        private ArrayList<Point> points = new ArrayList<>();

        @Override
        public void draw(Graphics g) {
            try {
                g.setColor(Color.WHITE);
                for (Point p : points) {
                    g.fillRect(p.x, p.y, 20, 20);
                }
            } catch (Exception ex) {
                System.out.println("Error drawing eraser: " + ex.getMessage());
            }
        }

        @Override
        public void update(Point p) {
            points.add(p);
        }
    }
}
