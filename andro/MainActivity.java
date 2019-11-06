package com.example.exercice9;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;
import java.lang.String;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.StrictMath.max;

public class MainActivity extends AppCompatActivity {
    Bitmap btmp;
    Bitmap btmp_oth;
    Bitmap btmp_oth2;
    ImageView image;
    Random r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.img_main);
        TextView tv = (TextView) findViewById(R.id.textView);
        Button boutton = (Button) findViewById(R.id.buttongris);
        Button boutton2 = (Button) findViewById(R.id.button);
        Button boutton_clear = (Button) findViewById(R.id.button3);
        Button boutton_chang = (Button) findViewById(R.id.button_chang);
        Button boutton_autre2 = (Button) findViewById(R.id.button_autre);
        Button boutton_egalGray = (Button) findViewById(R.id.button_egalGray);
        Button boutton_egal = (Button) findViewById(R.id.button_egal);

        btmp = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        btmp_oth = BitmapFactory.decodeResource(getResources(), R.drawable.test2);
        btmp_oth2 = BitmapFactory.decodeResource(getResources(), R.drawable.test6);

        String w = Integer.toString(btmp.getWidth());
        String h = Integer.toString(btmp.getHeight());
        r = new Random();

        tv.setText(w +" "+ h);


        boutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap cpy = btmp.copy(btmp.getConfig(), true);
                toGray2(cpy);
                image.setImageBitmap(cpy);
            }
        });

        boutton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap cpy = btmp.copy(btmp.getConfig(), true);
                contrasteDyn(cpy);
                image.setImageBitmap(cpy);
            }
        });

        boutton_clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                image.setImageBitmap(btmp);
            }
        });

        boutton_autre2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap cpy = btmp.copy(btmp.getConfig(), true);
                contrasteGrayDyn(cpy);
                image.setImageBitmap(cpy);
            }
        });

        boutton_chang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap cpy = btmp.copy(btmp.getConfig(), true);
                btmp = btmp_oth.copy(btmp_oth.getConfig(), true);
                btmp_oth = btmp_oth2.copy(btmp_oth2.getConfig(), true);
                btmp_oth2 = cpy.copy(cpy.getConfig(), true);
                image.setImageBitmap(btmp);
                String w = Integer.toString(btmp.getWidth());
                String h = Integer.toString(btmp.getHeight());
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText(w +" "+ h);
            }
        });

        boutton_egalGray.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap cpy = btmp.copy(btmp.getConfig(), true);
                contrasteEgalGray(cpy);
                image.setImageBitmap(cpy);
            }
        });

        boutton_egal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap cpy = btmp.copy(btmp.getConfig(), true);
                contrasteEgal(cpy);
                image.setImageBitmap(cpy);
            }
        });

        image.setImageBitmap(btmp);


    }

    void toGray(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        for(int i = 0; i<w; i++){
            for(int j=0; j <h; j++){
                int color = bmp.getPixel(i, j);
                float blue = Color.blue(color);
                float green = Color.green(color);
                float red = Color.red(color);
                int tmp = (int)(0.3 * red)+(int) (0.59*green)+ (int) (0.11*blue);
                bmp.setPixel(i, j, Color.rgb(tmp, tmp, tmp));
            }
        }

    }

    void toGray2(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        for(int i = 0; i<w*h; i++){
            int color = pixels[i];
            float blue = Color.blue(color);
            float green = Color.green(color);
            float red = Color.red(color);
            int tmp = (int)(0.3 * red)+(int) (0.59*green)+ (int) (0.11*blue);
            pixels[i] = Color.rgb(tmp, tmp, tmp);
        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    void colorize(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        float rand = (float) r.nextInt(360);

        for(int i = 0; i<w*h; i++){
            int color = pixels[i];
            float blue = Color.blue(color);
            float green = Color.green(color);
            float red = Color.red(color);
            float[] hsv;
            hsv = new float[3];
            rgb_to_hsv(red, blue, green, hsv);
            hsv[0] = rand;
            pixels[i] = hsv_to_rgb(hsv);

        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }


    void rgb_to_hsv(float red, float blue, float green, float[] hsv) {
        float red_ = red/255;
        float blue_ = blue/255;
        float green_ = green/255;

        float cmax = max(red_, blue_);
        cmax = max(cmax, green_);
        float cmin= min(red_, blue_);
        cmin = min(cmin, green_);

        float delta = cmax - cmin;

        float h;


        if(delta == 0){
            h = 0;
        }else{
            if(cmax == red_) {
                h = ((60 * (((green_ - blue_)/delta)))+360)%360;
            }else if(cmax == green_) {
                h = (60 *(((blue_ - red_)/delta) ))+120;
            }else {
                h = 60 *(((red_ - green_)/delta))+240;
            }
        }

        float s;

        if(cmax == 0) {
            s = 0;
        }else {
            s = 1 -(cmin/cmax);
        }

        float v = cmax;




        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
        return;
    }

    int hsv_to_rgb(float hsv[]){
        float t = (int) (hsv[0]/60)%6;
        float f = (hsv[0]/60)- t;
        float l = hsv[2] *(1 - hsv[1]);
        float m = hsv[2] * (1- f*hsv[1]);
        float n = hsv[2] * (1-(1-f) * hsv[1]);


        float red =0;
        float green=0;
        float blue=0;

        if(t ==0){
            red = hsv[2];
            green = n;
            blue = l;
        }else if(t==1){
            red = m;
            green = hsv[2];
            blue = l;
        }else if(t == 2){
            red = l;
            green = hsv[2];
            blue = n;
        }else if(t == 3){
            red = l;
            green = m;
            blue = hsv[2];
        }else if(t == 4){
            red = n;
            green = l;
            blue = hsv[2];
        }else if(t == 5){
            red = hsv[2];
            green = l;
            blue = m;
        }
        int rgb = Color.rgb(red, green, blue);

        return rgb;

    }

    void conserve(Bitmap bmp, float mid){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        float rand = (float) r.nextInt(360);

        for(int i = 0; i<w*h; i++){
            int color = pixels[i];
            float blue = Color.blue(color);
            float green = Color.green(color);
            float red = Color.red(color);
            float[] hsv;
            hsv = new float[3];
            rgb_to_hsv(red, blue, green, hsv);
            float angle = hsv[0];
            boolean b = false;
            if(mid +30 >= 360 || mid -30 < 0){
                if(angle> mid -30  && angle < mid + 30){
                    b =true;
                }
                if(mid -30 < 0){
                    mid = mid +360;
                    if(angle> mid -30  && angle < mid + 30){
                        b =true;
                    }
                    mid = mid -360;
                }
            }else if(angle > mid -30 && angle < mid + 30){
                b = true;
            }
            if(b){
                pixels[i] = hsv_to_rgb(hsv);
            }else{
                hsv[1] = 0;
                pixels[i] = hsv_to_rgb(hsv);
            }

        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    int[] histo(Bitmap bmp, char flag){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        int histo[];
        histo = new int[256];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);


        for(int i = 0; i<h*w; i++){
            int color = pixels[i];
            if(flag == 'b'){
                int blue = Color.blue(color);
                histo[blue] =  histo[blue] +1;
            }else if(flag == 'g'){
                int green = Color.green(color);
                histo[green] =  histo[green] +1;
            }else if(flag == 'r'){
                int red = Color.red(color);
                histo[red] =  histo[red] +1;
            }
        }
        return(histo);
    }

    void contrasteGrayDyn(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        int[] LUT;
        LUT = new int[256];
        int[] I;
        I = new int[w*h];


        int min =255;
        int max = 0;

        for(int x = 0; x<w*h; x++){
            int color = pixels[x];
            int blue = Color.blue(color);
            if(blue > max){
                max = blue;
            }
            if(blue < min){
                min = blue;
            }
        }

        for(int ng = 0; ng< 256; ng++){
            LUT[ng] = (255 * (ng - min))/(max - min);
        }


        for(int z = 0; z<w*h; z++){
            I[z] = LUT[Color.blue(pixels[z])];
            int color = I[z];
            pixels[z] = Color.rgb(color, color, color);
        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);

    }

    void contrasteDyn(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);



        int min_b =255;
        int max_b = 0;
        int min_g =255;
        int max_g = 0;
        int min_r =255;
        int max_r = 0;


        for(int x = 0; x<w*h; x++){
            int color = pixels[x];
            int blue = Color.blue(color);
            if(blue > max_b){
                max_b = blue;
            }
            if(blue < min_b){
                min_b = blue;
            }
            int green = Color.green(color);
            if(green > max_g){
                max_g = green;
            }
            if(green < min_g){
                min_g = green;
            }
            int red = Color.red(color);
            if(red > max_r){
                max_r = red;
            }
            if(red < min_r){
                min_r = red;
            }
        }

        int[] LUT_r;
        LUT_r = new int[256];

        int[] LUT_g;
        LUT_g = new int[256];

        int[] LUT_b;
        LUT_b = new int[256];

        for(int ng = 0; ng< 256; ng++){
            LUT_r[ng] = (255 * (ng - min_r))/(max_r - min_r);
        }
        for(int ng = 0; ng< 256; ng++){
            LUT_g[ng] = (255 * (ng - min_g))/(max_g - min_g);
        }
        for(int ng = 0; ng< 256; ng++){
            LUT_b[ng] = (255 * (ng - min_b))/(max_b - min_b);
        }


        for(int z = 0; z<w*h; z++){
            int color = pixels[z];
            int tmp_r = LUT_r[(Color.red(color))];
            int tmp_g = LUT_g[(Color.green(color))];
            int tmp_b = LUT_b[(Color.blue(color))];
            pixels[z] = Color.rgb(tmp_r, tmp_g, tmp_b);
        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);

    }

    void contrasteEgalGray(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        int[] H;
        H = histo(bmp, 'r');
        int[] c;
        c = new int[256];
        c[0]=H[0];


        for(int i = 0; i < 255; i++){
            c[i+1] = c[i] + H[i+1];
        }


        for(int i = 0; i < w*h; i++){
            int color = pixels[i];
            long tmp =(((long)c[Color.red(color)]*(long)255));
            tmp = tmp / (long)(w*h);
            int tmpi = (int) tmp;
            pixels[i] = Color.rgb(tmpi, tmpi, tmpi);
        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    void contrasteEgal(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int pixels[];
        pixels = new int[w*h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        int[] Hr;
        Hr = histo(bmp, 'r');
        int[] cr;
        cr = new int[256];
        cr[0]=Hr[0];
        int[] Hg;
        Hg = histo(bmp, 'g');
        int[] cg;
        cg = new int[256];
        cg[0]=Hg[0];
        int[] Hb;
        Hb = histo(bmp, 'b');
        int[] cb;
        cb = new int[256];
        cb[0]=Hb[0];

        for(int i = 1; i < 256; i++){
            cr[i] = cr[i-1] + Hr[i];
            cg[i] = cg[i-1] + Hg[i];
            cb[i] = cb[i-1] + Hb[i];
        }

        for(int i = 0; i < w*h; i++){
            int color = pixels[i];
            long tmpr = ((long)(cr[Color.red(color)]*(long)255)/(long)(w*h));
            long tmpg = ((long)(cg[Color.green(color)]*(long)255)/(long)(w*h));
            long tmpb = ((long)(cb[Color.blue(color)]*(long)255)/(long)(w*h));
            pixels[i] = Color.rgb((int)tmpr, (int)tmpg, (int)tmpb);
        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);

    }

}
