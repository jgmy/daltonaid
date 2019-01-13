/**
 * <p>Daltonaid</p>
 * <p>Updated: 2018-12-12 José G Moya Y.</p>
 */

import ketai.camera.*;
import android.content.res.Resources;
import android.os.Build;
import java.util.Locale;
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
   
void setup() {
 
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
  fontHeight=int (textAscent ()+textDescent ());
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
       lang=int(languages[f][1]);
       break;
     }
   }
 ;
}

void draw (){
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


void drawSplashScreen (){
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


void drawUI(){
  
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

void onCameraPreviewEvent()
{ 
  //println("ready");
  cam.read();
  //foto= cam.get();
    //applyFilters();
     //image (foto,width/2, height/2,width,height);
}



// start/stop camera preview by tapping the screen
void mousePressed()
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
void PermissionRes(boolean granted){
  if (granted){
    println("cam.start from PermissionRes");
    cam.start();
    cam.autoSettings();
  } else {
    println("No permission");
  }
}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == MENU) {
      if (cam.isFlashEnabled())
        cam.disableFlash();
      else
        cam.enableFlash();
    }
  }
}
