package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {

    private int height;
    private int width;
    private double maxRatio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        // TODO: проверить на максимальное допустимое соотношение сторон
        // TODO: если картинка не подходит, то выбросить BadImageSizeException
        // TODO: если выставлены допустимые размеры по ширине и/или высоте, то пропорционально уменьшить новые ширину и высоту

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH); // изменяем размер
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY); // делаем картинку черно-белой
        Graphics2D graphics = bwImg.createGraphics(); // инструмент для рисования
        graphics.drawImage(scaledImage, 0, 0, null); // делаем картинку доступной для рисования
        WritableRaster bwRaster = bwImg.getRaster(); // инструмент для проходов по пикселям

        char[][] screen = new char[getMaxWidth()][getMaxHeight()];
        for (int w = 0; w < getMaxWidth(); w++) { // TODO: вернуть в виде текста
            for (int h = 0; h < getMaxHeight(); h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0]; // w - номер столбца, h - номер строки, new int[3] - цвет (red, green, blue)
                char c = schema.convert(color);
                screen[w][h] = c;
            }
        }
        return printAll(screen); // Возвращаем собранный текст.
    }

    private String printAll(char[][] screen) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < getMaxWidth(); i++) {
            for (int j = 0; j < getMaxHeight(); j++) {
                st.append(screen[i][j]);
            }
            st.append("\n");
        }
        return st.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
        // чтобы задать максимальную ширину из Main
    }

    public int getMaxWidth() {
        return width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
        // чтобы задать максимальную высоту из Main
    }

    public int getMaxHeight() {
        return height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
        // чтобы задать максимально допустимое соотношение сторон из Main,
        // если выходит за пределы то выбросить исключение
    }

    public double getMaxRatio() {
        return maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
        // чтобы задать цветовую схему в Main
    }

}
