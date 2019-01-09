package com.josemoyagmail.com.daltonaid;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.camera.*; 
import android.content.res.Resources; 
import android.os.Build; 
import java.util.Locale; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class daltonaid extends PApplet {

/**
 * <p>Daltonaid</p>
 * <p>Updated: 2018-12-12 José G Moya Y.</p>
 */





String camoffstr[]={
"Clic en imagen para iniciar cámara",
"Touch image to start camera"};
String camonstr[]={
  "Clic en imagen para detener cámara",
  "Touch image to stop camera"
};


KetaiCamera cam;
int lang=1; /*default language*/
PImage foto;
boolean camReady;
PFont tipografia;
int fontHeight;
PImage splashScreen;
boolean splash;
float splashend;

Locale locale; 
   
public void setup() {
  orientation(LANDSCAPE);
  
  frameRate  (25);
  //splashScreen=loadImage ("Daltonaid_splash_2650x1600.jpg");
  cam = new KetaiCamera(this,min(width,1024),min(height,768), 24);
  foto=createImage(min(width,1024),min(height,768),ARGB);
  imageMode(CENTER);
  fontHeight= height/20;
  tipografia=createFont ("SansSerif", fontHeight );
  textFont (tipografia);
  textAlign (CENTER );
  fontHeight=PApplet.parseInt (textAscent ()+textDescent ());
  camReady=true;
  /* debug
  createIcon (64,64);
  createIcon (96,96);
  createIcon (48,48);
  createIcon (128,128);
  */
  splash=true;
  splashend=millis() + 5000;
  
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
   { 
     locale = Resources.getSystem().getConfiguration().getLocales().get(0);
   } else { 
        //noinspection deprecation
        locale = Resources.getSystem().getConfiguration().locale;
   }
   println (locale.getLanguage());
   
   for (int f=0; f<languages.length; f++){
     if ( languages[f][0]==locale.getLanguage()){
       lang=PApplet.parseInt(languages[f][1]);
       break;
     }
   }
 ;
}

public void draw (){
  if (splash){ 
    drawSplashScreen();
    if (millis()>splashend){
      splash=false;
      pushStyle ();
      fill (64,64,64,128);
      rect(0,0,width,height);
      popStyle ();
    }
  }else {
    drawUI ();
  }
}


public void drawSplashScreen (){
  if (frameCount<=1){
    createLogo ();
  }
 // image (BigLogo,width/2,height/2);
  if (frameCount <25){
    background (lerp (10,200,frameCount/25));
    tint (255,255,255,(10*frameCount));
    image (BigLogo,width/2,height/2);
    noTint ();
  } else {
    background (200,200,200);
    if (frameCount>100){
      tint (255,255,255,lerp (255, 100,( frameCount-100)/25));
      image (BigLogo,width/2,height/2);
      noTint ();
    } else {
      noTint ();
      image (BigLogo,width/2,height/2);
    }
  }
}


public void drawUI(){
  
  //background  (128,128,128 );
  fill (0);
  if (cam !=null && cam.isStarted()){
    image (cam,width/2, height/2,width,height);
  } 
  if (cam!=null){
     foto= cam.get(0,0,cam.width,cam.height);
  
     applyFilters();
     image (foto,width/2, height/2,width,height);
  }
  
  /* Gui */
  rect (0,0, width,fontHeight);
  rect (0,height-2*(fontHeight+4),width,height);
  
  fill (255);
  if (! cam.isStarted){
   text(camoffstr[lang] , width/2, fontHeight);
   //text(locale.getLanguage() , width/2, fontHeight);
  } else {
   text(camonstr[lang] , width/2, fontHeight);
  }
  fill(180);
  textAlign(LEFT);
  text (cfiltro[(filtro+numfiltros-1)%numfiltros][lang]
  , 5,height- (fontHeight+4)/2);
  textAlign(RIGHT);
  text (cfiltro[(filtro+1)%numfiltros][lang] , width-5,height- (fontHeight+4)/2);
  textAlign(CENTER);
  fill(255);
  text (cfiltro[filtro][lang], width/2,height- (fontHeight+4)/2);
  
}

public void onCameraPreviewEvent()
{ 
  //println("ready");
  cam.read();
  //foto= cam.get();
    //applyFilters();
     //image (foto,width/2, height/2,width,height);
}



// start/stop camera preview by tapping the screen
public void mousePressed()
{
  if (splash){
    splash=false;
    pushStyle ();
    fill (64,64,64,128);
    rect (0,0,width,height);
    popStyle ();
    frameRate ( 25);
    return;
  }
  if (mouseY>=( height-2*(fontHeight+4))){
    if(mouseX>width/2){
      filtro=(filtro+1) % numfiltros;
    } else if(mouseX<width/2){
      filtro=(filtro+numfiltros-1) % numfiltros;
    }
    
    return;
  }
  if (cam.isStarted()) {
    println("cam.stop()");
      cam.stop();
      
  } else {
      if (!hasPermission("android.permission.CAMERA") ){
         requestPermission("android.permission.CAMERA", "PermissionRes");
      } else {
        println("cam.start from mousepressed");
        cam.start();
        
        cam.autoSettings();
      }
  }
}
public void PermissionRes(boolean granted){
  if (granted){
    println("cam.start from PermissionRes");
    cam.start();
    cam.autoSettings();
  } else {
    println("No permission");
  }
}

public void keyPressed() {
  if (key == CODED) {
    if (keyCode == MENU) {
      if (cam.isFlashEnabled())
        cam.disableFlash();
      else
        cam.enableFlash();
    }
  }
}
PGraphics BigLogo;
public void createLogo (){
  PGraphics pg,pg2;
  //image (splashScreen,width/2,height/2, width, height);
  pushStyle ();
  pg=createGraphics (width/2,height);
  pg2=createGraphics (width/2,height);
  BigLogo=createGraphics (width,height);
    pg.beginDraw ();
    pg.background (200,200,200,0);
    pg.stroke (color (255,255,255));
    pg.strokeWeight(4);
    pg.ellipseMode (CENTER);
    pg.fill (color (0,0,255));
    pg.ellipse (width/2,height/2,height/2-10,height/2-10);
    
    pg.textSize (height/4);
    float tf=pg.textAscent () +pg.textDescent ();
    pg.textAlign (RIGHT);
    pg.fill (color (200,0,0));
    pg.text ("á ",width/2,(height+tf)/2);
    pg.fill (color (0,0,0));
    pg.text ("a ",width/2,(height+tf)/2);
    pg.endDraw ();
    pg2.beginDraw ();
    pg2.background (200,200,200,0);
    pg2.strokeWeight(4);
    pg2.stroke (color (255,255,255));
    pg2.ellipseMode (CENTER);
    pg2.fill (color (255,0,0));
    pg2.ellipse (0,height/2,height/2-10,height/2-10);
    pg2.textSize (height/4);
    tf=pg2.textAscent () +pg.textDescent ();
    pg2.textAlign  (LEFT);
    pg2.fill (color (0,0,200));
    pg2.text (" á", 0,(height+tf)/2);
    pg2.fill (0);
    pg2.text (" a", 0,(height+tf)/2);
    pg2.textAlign (CENTER );
    pg2.endDraw ();
    BigLogo.beginDraw ();
    BigLogo.textAlign (CENTER); 
    BigLogo.imageMode (CENTER);
    BigLogo.image (pg,width/4, height/2);
    BigLogo.image (pg2,width*0.75f, height/2);
    BigLogo.textSize (height/4);
    BigLogo.fill (255,255,255, 255);
    BigLogo.text (">",width/2, (height+tf)/2);
    BigLogo.text ("daltonaid",width/2,height-10);
    BigLogo.endDraw ();
  popStyle ();

}

public void createIcon(int w,int h){
    PGraphics pg,pg2;
    PGraphics Icono;
  //image (splashScreen,w/2,h/2, w, h);
  pushStyle ();
  pg=createGraphics (w/2,h);
  pg2=createGraphics (w/2,h);
  Icono=createGraphics (w,h);
    pg.beginDraw ();
    pg.background (200,200,200,0);
    pg.stroke (color (255,255,255));
    pg.strokeWeight(4);
    pg.ellipseMode (CENTER);
    pg.fill (color (0,0,255));
    pg.ellipse (w/2,h/2,h/2-1,h/2-1);
    
    pg.textSize (h/4);
    float tf=pg.textAscent () +pg.textDescent ();
    pg.textAlign (RIGHT);
    pg.fill (color (200,0,0));
    pg.text ("á ",w/2,(h+tf)/2);
    pg.fill (color (0,0,0));
    pg.text ("a ",w/2,(h+tf)/2);
    pg.endDraw ();
    pg2.beginDraw ();
    pg2.background (200,200,200,0);
    pg2.strokeWeight(4);
    pg2.stroke (color (255,255,255));
    pg2.ellipseMode (CENTER);
    pg2.fill (color (255,0,0));
    pg2.ellipse (0,h/2,h/2-1,h/2-1);
    pg2.textSize (h/4);
    tf=pg2.textAscent () +pg.textDescent ();
    pg2.textAlign  (LEFT);
    pg2.fill (color (0,0,200));
    pg2.text (" á", 0,(h+tf)/2);
    pg2.fill (0);
    pg2.text (" a", 0,(height+tf)/2);
    pg2.textAlign (CENTER );
    pg2.endDraw ();
    Icono.beginDraw ();
    Icono.textAlign (CENTER); 
    Icono.imageMode (CENTER);
    Icono.image (pg,w/4, h/2);
    Icono.image (pg2,w*0.75f, h/2);
    Icono.textSize (h/4);
    Icono.fill (255,255,255, 255);
    Icono.text (">",w/2, (h+tf)/2);
    Icono.endDraw ();
  popStyle ();
  /* You could need change permissions for this to work */
  Icono.save ("/sdcard/Daltonaid-Icono-"+str (w)+"x"+str (h)+".png");
  
}
int filtro=8;
int prevfiltro=0;
int numfiltros=9;
String[] cfiltro[]={
   {"Rojo×Azul","Red⇄Blue"},
   {"Rojo×Verde","Red⇄Green"}, 
   {"Azul×Verde","Blue⇄Green"},
   {"RojVerAz⇄AzRojVer",
   "RedGreBlu⇄BluRedGre"
   },
   {
   "RojVerAz⇄VerAzRoj",
   "RedGreBlu⇄BluRedGre"
   },
   {"RojoBrilla","RedGlows"},
   {"DobleRojo","DoubleRed"},
   {"ColorPlano","PlainColor"},
   {"Natural","Natural"},
   };
   
String[] languages[]={
  {"es","0"},
  {"en","1"}
  };

public void applyFilters(){
  //if (camReady) {
  //camReady=false;
  //cam.pause ();
  float t=millis();
  switch (filtro){
    case 0: redtoblue ();
      break;
    case 1: redtogreen ();
     break;
    case 2: bluetogreen ();
     break;
    case 3: blueredgreen ();
     break;
    case 4: greenbluered();
     break;
     case 5: redbright();
     break;
    case 6: morered();
     break;
    case 7: plaincolors();
     break;
    case 8: 
    default:
       nofilter ();
   }
   if (prevfiltro!=filtro){
    prevfiltro=filtro;
    println ("delay "+
      cfiltro[filtro] +":"+
      nf((millis()-t),5,3)+
      "ms"
    );
  //
  }
   // cam.resume ();
  camReady=true;
  //}
  
  
}
public void redtoblue (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    /*
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
    foto.pixels [i]=r 
        | (g <<8) 
        | (b <<16) |0xFF000000;
    */
  // foto.pixels [i]=color (b, g, r,a);
   foto.pixels[i] =(
        (argb &  0xFF00FF00) | 
        ((argb & 0x00FF0000 ) >>16) |
        ((argb & 0x000000FF )<<16)
        );
  }
  foto.updatePixels ();
  
}
public void redbrightold (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
    if (r>0x80) {
      r=min(255,r*2);
      g=min(255,g*2);
      b=min(255,b*2);
      foto.pixels [i]=r
        | (g <<8) 
        | (b <<16) |0xFF000000;
      }
  }
  foto.updatePixels ();
  
}
public void redbright (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
     int increase=(r>>1);
      r=min(255,r+increase);
      g=min(255,g+increase);
      b=min(255,b+increase);
      foto.pixels [i]=(r<<16)
        | (g <<8) 
        | (b ) |0xFF000000;
      
  }
  foto.updatePixels ();
  
}


public void redtogreen (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    /*
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
   foto.pixels [i]=color (g, r, b);
   */
   foto.pixels[i]=(
        (argb &  0xFF0000FF) | 
        ((argb & 0x00FF0000 ) >>8) |
        ((argb & 0x0000FF00) <<8)
        );

   /*
   foto.pixels [i]=r 
        | (g <<8) 
        | (b <<16) ;
   */
  }
  foto.updatePixels ();
}
public void bluetogreen (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    /*
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
   
   foto.pixels [i]=color (r,b,g);
   */
   foto.pixels[i] =(
        (argb &  0xFFFF0000) | 
        ((argb & 0x0000FF00 ) >>8) |
        ((argb & 0x000000FF) <<8)
        );
        
  }
  foto.updatePixels ();
}

public void blueredgreen (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
    foto.pixels [i]=color (b, r, g, a);

   /*
   foto.pixels [i]=r 
        | (g <<8) 
        | (b <<16) ;
   */
  }
  foto.updatePixels ();

}


public void greenbluered(){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
    int a = (argb >> 24) & 0xFF;
    int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    int b = argb & 0xFF; 
   
   foto.pixels [i]=color (g,b, r, a);

   /*
   foto.pixels [i]=r 
        | (g <<8) 
        | (b <<16) ;
   */
  }
  foto.updatePixels ();
}


public void morered(){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];
   // int a = (argb >> 24) & 0xFF;
   // int r = (argb >> 16) & 0xFF;  // Faster way of getting red(argb)
    // int g = (argb >> 8) & 0xFF;   // Faster way of getting green(argb)
    // int b = argb & 0xFF; 
   // multiply red by 2
   foto.pixels[i]=
     (argb & 0xFF00FFFF) |
     (((argb& 0xFF0000)<<2)
        & 0xFF0000) |
        ((argb & 0x400000)<<1) |
       (argb & 0x800000) ;
   
  }
  foto.updatePixels ();
}

// void plaincolors(){
//  int pcount=foto.height*foto.width;
//  foto.loadPixels ();
//  for (int i=0; i<pcount; i++){
//    color argb= foto.pixels [i];
//    
//   // multiply all colors by 2,
//   // keep values if bigger
//   foto.pixels[i]=
//     (argb & 0xFF000000) |
//     (((argb & 0x5F0000)<<2)
//        & 0xFF0000) |
//        ((argb & 0x400000)<<1) |
//        (argb & 0x800000)
//      |
//     (((argb & 0x5F00)<<2)
//        & 0xFF00) |
//        ((argb & 0x4000)<<1) |
//        (argb & 0x8000)
//      |
//     (((argb & 0x5F)<<2)
//        & 0xFF) |
//        ((argb & 0x40)<<1) |
//        (argb & 0x80);
//   
//  }
//  foto.updatePixels ();
//}

public void plaincolors(){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    int argb= foto.pixels [i];

   int argb2=0;
   int msk=0xFF0000;
   for (int bt=0; bt<3; bt+=1 ){
     msk=msk>>8;
     
       argb2|= (
        ((
            (
             ( argb & msk) >> 4
            ) & msk
            )<<4)
           & msk);
   }
   foto.pixels[i]=(argb&0xFF000000) | argb2;
     
   
  }
  foto.updatePixels ();
}

public void nofilter (){
  /* do nothing */
}
  public void settings() {  fullScreen(); }
}
