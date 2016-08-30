/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ilarkesto.junit.misc;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import static com.itextpdf.text.pdf.BaseFont.EMBEDDED;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 *
 */
public class FontTest {

    public static final String FONT_UBUNTU = "src/main/resources/fonts/Ubuntu-R.ttf";
    public static final String FONT_LIBERATION_MONO = "src/main/resources/fonts/LiberationMono-Regular.ttf";

    public FontTest() {
    }

    @Test
    public void checkFont1() {
        File fontFile = new File(FONT_UBUNTU);
        assertTrue("The FONT_UBUNTU file is not found.", fontFile.exists());
    }

    @Test
    public void createUbuntu() {
        assertTrue("Cannot create FONT_UBUNTU.", createFont(FONT_UBUNTU));
    }

    @Test
    public void checkFont2() {
        File fontFile = new File(FONT_LIBERATION_MONO);
        assertTrue("The FONT_LIBERATION_MONO file is not found.", fontFile.exists());
    }
    
    @Test
    public void createLiberation() {
        assertTrue("Cannot create FONT_LIBERATION_MONO.", createFont(FONT_LIBERATION_MONO));
    }
    
    @Test
    public void chineseFont() {
        assertTrue("Cannot create STSong-Light.", createFont("STSong-Light")); // simplified chinese
    }
    
    private boolean createFont(String name) {
        Font font;
        try {
            font = new Font(BaseFont.createFont(name, IDENTITY_H, EMBEDDED));
            return true;
        } catch (DocumentException | IOException ex) {
            return false;
        }
    }

}
