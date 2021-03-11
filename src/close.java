import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/*
 * For run .jar
 * java -jar -Djava.library.path=/usr/lib/java  File.jar
 * 
 * -Djava.library.path= <dir di java-opencv>  -> su Linux è un file .so, su Windows un .dll
 * */

public class close {

    private static JFrame frame = new JFrame();

	
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture video = new VideoCapture();
        Mat f = new Mat();
        video.open(0);

        while (true){
            video.read(f);
            showResult(f);
        }
    } 

    public static void showResult(Mat img) {
        Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte m = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, m);
        byte[] byteArray = m.toArray();
        BufferedImage bufImage = null;
        try {
    	    frame.getContentPane().removeAll();
    	
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
            frame.revalidate();
        }

        catch (Exception e) {
        e.printStackTrace();
        }
    }
}