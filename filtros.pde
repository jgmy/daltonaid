int filtro=8;
int prevfiltro=0;
int numfiltros=9;
String cfiltro[]={
   "Rojo×Azul",
   "Rojo×Verde", 
   "Azul×Verde",
   "RojVerAz->AzRojVer",
   "RojVerAz->VerAzRoj",
   "RojoBrilla",
   "DobleRojo",
   "ColorPlano",
   "Natural"
   };

void applyFilters(){
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
void redtoblue (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
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
void redbrightold (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
    
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
void redbright (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
    
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


void redtogreen (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
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
void bluetogreen (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
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

void blueredgreen (){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
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


void greenbluered(){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
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


void morered(){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];
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

void plaincolors(){
  int pcount=foto.height*foto.width;
  foto.loadPixels ();
  for (int i=0; i<pcount; i++){
    color argb= foto.pixels [i];

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

void nofilter (){
  /* do nothing */
}
