PGraphics BigLogo;
void createLogo (){
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
    BigLogo.image (pg2,width*0.75, height/2);
    BigLogo.textSize (height/4);
    BigLogo.fill (255,255,255, 255);
    BigLogo.text (">",width/2, (height+tf)/2);
    BigLogo.text ("daltonaid",width/2,height-10);
    BigLogo.endDraw ();
  popStyle ();

}

void createIcon(int w,int h){
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
    Icono.image (pg2,w*0.75, h/2);
    Icono.textSize (h/4);
    Icono.fill (255,255,255, 255);
    Icono.text (">",w/2, (h+tf)/2);
    Icono.endDraw ();
  popStyle ();
  /* You could need change permissions for this to work */
  Icono.save ("/sdcard/Daltonaid-Icono-"+str (w)+"x"+str (h)+".png");
  
}
