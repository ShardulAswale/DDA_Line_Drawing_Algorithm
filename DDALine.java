package raster;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

class ThirdGLEventListener implements GLEventListener {

    private GLU glu;

    @Override
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        glu = new GLU();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glViewport(0, 0, 600, 600);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 640, 0, 480);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        drawLine(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable,
            boolean modeChanged, boolean deviceChanged) {
    }

    private void drawLine(GL gl) {
        drawPolygon(gl,200d,200d,0d,3,40,1);
        drawPolygon(gl,200d,200d,0d,4,80,2);
        drawPolygon(gl,200d,200d,0d,5,120,3);
        drawPolygon(gl,200d,200d,0d,6,160,4);
//        drawThickLine(gl,460d,360d,410d,410d, 0d, 50);
    }

    public void drawThinLine(GL gl, double x1, double y1, double x2, double y2) {
        double len = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
        double dx = (x2 - x1) / len, dy = (y2 - y1) / len;
        double x = (x1 + 0.5 * sign(dx));
        double y = (y1 + 0.5 * sign(dx));
        gl.glBegin(GL.GL_POINTS);
        for (int i = 0; i <= len; i++) {
            gl.glVertex2d(Math.floor(x), Math.floor(y));
            x = x + dx;
            y = y + dy;
        }
        gl.glEnd();
    }

    public void drawThickLine(GL gl, double x1, double y1, double x2, double y2, double a, int size,int s) {
        for (float i = -size / 2; i < size / 2; i=(float) (i+0.1)) {
            drawThinLine(gl, x1 + i * Math.cos(a + ((Math.PI / 2) - (Math.PI/s))), y1 + i * Math.sin(a + ((Math.PI / 2) - (Math.PI/s))), x2 + i * Math.cos(a + ((Math.PI / 2) + (Math.PI/s))), y2 + i * Math.sin(a + ((Math.PI / 2) + (Math.PI/s))));
        }
    }

    public void drawDottedLine(GL gl, double x1, double y1, double x2, double y2, int size) {
        double len = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
        double dx = (x2 - x1) / len, dy = (y2 - y1) / len;
        double x = x1 + 0.5 * sign(dx);
        double y = y1 + 0.5 * sign(dx);
        gl.glBegin(GL.GL_POINTS);
        boolean flag = false;
        for (int i = 0; i <= len; i++) {
            if (i % size == 0) {
                flag = !flag;
            }
            if (flag == true) {
                gl.glVertex2d((int) Math.floor(x), (int) Math.floor(y));
            }
            x = x + dx;
            y = y + dy;
        }
        gl.glEnd();
    }

    public int sign(double a) {
        if (a < 0) {
            return 1;
        } else if (a == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public void drawPolygon(GL gl, double x, double y, double a, int sides, int size, int type) {
        double inc_angle = 2 * (Math.PI / sides);
        x = x - size / 2;
        y = y - (size / (2 * Math.tan(Math.PI / sides)));
        for (int i = 0; i < sides; i++) {
            switch (type) {
                case 1:
                    drawThinLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)));
                    break;
                case 2:
                    drawThickLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)), a, 40,sides);
                    break;
                case 3:
                    drawDottedLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)), 10);
                    break;
                case 4:
                    drawDottedLine(gl, x, y, (x + size * Math.cos(a)), (y + size * Math.sin(a)), 1);
                    break;
            }
            x = (x + size * Math.cos(a));
            y = (y + size * Math.sin(a));
            a += inc_angle;
        }
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}

public class DDALine {

    public static void main(String args[]) {
        GLCapabilities capabilities = new GLCapabilities();
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        ThirdGLEventListener b = new ThirdGLEventListener();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(400, 400);
        final JFrame frame = new JFrame("Basic frame");
        frame.add(glcanvas);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
