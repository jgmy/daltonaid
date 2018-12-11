package com.josemoyablogspotcom.daltonaid;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.camera.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class DaltonAid extends PApplet {



/**
 * <p>Daltonaid</p>
 * <p>Updated: 2015-01-06 Jos\u00e9 G Moya Y.</p>
 */
PFont tipografia;

PImage foto;
int filtro=0;
int numfiltros=5;
KetaiCamera cam;
boolean camReady;
int fontHeight;
boolean splash;
float splashend;
String cfiltro[]={
   "Rojo\u00d7Azul",
   "Rojo\u00d7Verde", 
   "Azul\u00d7Verde",
   "RojVerAz->AzRojVer",
   "RojVerAz- >VerAzRoj",
   "Natural"};
   PImage splashScreen;
public void setup() {
  filtro=0;
  //splashScreen=loadImage ("Daltonaid_splash_2650x1600.jpg");
  splash=true;
  splashend=millis() + 5000;
  orientation(LANDSCAPE);
  imageMode(CENTER);
  cam = new KetaiCamera(this, width, height, 25);
  frameRate  (25);
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
}

public void draw (){
  if (splash){ 
    draw1 ();
    if (millis()>splashend){
      splash=false;
      pushStyle ();
      fill (64,64,64,128);
      rect(0,0,width,height);
      popStyle ();
      frameRate ( 10);
    }
  }else {
    draw2 ();
  }
}


public void draw1 (){
  if (frameCount<=1){
    createLogo ();
  }
 // image (BigLogo,width/2,height/2);
  if (frameCount <25){
    background (lerp (10,200,frameCount/25));
    tint (255,255,255,(10*frameCount));
    image (BigLogo,width/2,height/2);
    noTint ();
  } else{
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
  pg.text ("\u00e1 ",width/2,(height+tf)/2);
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
  pg2.text (" \u00e1", 0,(height+tf)/2);
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
  pg.text ("\u00e1 ",w/2,(h+tf)/2);
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
  pg2.text (" \u00e1", 0,(h+tf)/2);
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
Icono.save ("/sdcard/Daltonaid-Icono-"+str (w)+"x"+str (h)+".png");

}

public void draw2(){
  
  //background  (128,128,128 );
  fill (0);
  rect (0,0, width,fontHeight);
  rect (0,height-2*(fontHeight+4),width,height);
  
  fill (255);
  if (! cam.isStarted){
   text("Clic en imagen para iniciar c\u00e1mara" , width/2, fontHeight);
  } else {
   text("Clic en imagen para detener c\u00e1mara" , width/2, fontHeight);
  }
  
  text ("Filtro: " + cfiltro[filtro]+" (cambiar)", width/2,height- (fontHeight+4)/2);
  if (camReady && cam.isStarted ()){
     // image(foto, width/2, height/2);
  }
}

public void onCameraPreviewEvent()
{ 
  //if (camReady) {
  cam.read();
  //cam.pause ();
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
    case 5: 
    default:
       nofilter ();
    
  }
 
  //cam.resume ();
  //}
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
    frameRate ( 10);
    return;
  }
  if (mouseY>=( height-2*(fontHeight+4))){
    filtro=(filtro+1) % numfiltros;
    return;
  }
  if (cam.isStarted()) {
      cam.stop();
  } else {
      cam.start();
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
public void redtoblue (){
  foto= cam.get ();
  int pcount=foto.height*foto.width;
  camReady=false;
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
  camReady=true;
  image (foto,width/2, height/2);
  redraw ();
}

public void redtogreen (){
  foto= cam.get ();
  int pcount=foto.height*foto.width;
  camReady=false;
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
  camReady=true;
  image (foto,width/2, height/2);
  redraw ();
}
public void bluetogreen (){
  foto= cam.get ();
  int pcount=foto.height*foto.width;
  camReady=false;
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
  camReady=true;
  image (foto,width/2, height/2);
  redraw ();
}

public void blueredgreen (){
  foto= cam.get ();
  int pcount=foto.height*foto.width;
  camReady=false;
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
  camReady=true;
  image (foto,width/2, height/2);
  redraw ();
}
public void greenbluered(){
  foto= cam.get ();
  int pcount=foto.height*foto.width;
  camReady=false;
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
  camReady=true;
  image (foto,width/2, height/2);
  redraw ();
}
public void nofilter (){
  foto= cam.get ();
  camReady=true;
  image (foto,width/2, height/2);
  redraw ();
}

}
