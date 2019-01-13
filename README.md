# Daltonaid

A tool to aid colorblind people distinguish between red and black.

## Getting Started

You can compile this project using android mode of processing, or APDE.
A gradle project file is included, also.
Take into consideration that you might need deleting /daltonaid/AndroidManifest.xml if you are using APDE. This will produce an APK that requires autofocus capabilities (APDE can't insert the "uses-feature" required to mark autofocus as optional).

### Prerequisites
Daltonaid runs on Android OS. Older versions run on Kitkat, current works on Oreo.
 * Processing: http://processing.org along with processing Android Mode.
 * Alternatively, you can use APDE from https://github.com/Calsign/APDE/wiki or google play. 
 * In case you use Android Studio or Gradle, a static processing.jar library is included.
 * Ketai library: http://ketai.org/
 * In case you use Android Studio or Gradle, a static ketai.jar library is included.

### Installing
You can compile this app with the android mode of the Processing language, or from APDE. 
Personally, I used APDE.

#### Building on APDE:
 * Download APDE from Google Play on your android phone.
 * Make sure your Sketchbook location is on the external storage: Menu-> Settings -> General -> Build Location.
 * Download ketai library and install it: Menu -> Tools -> Import external library.
 * Clone this repository: Menu -> Tools -> Git Repository -> Clone 
 ** Remote: http://github.com/jgmy/daltonaid.git
 ** Local: select any name you want.
 ** User: select your github user. Hopefully, a blank user and password should work for clone.
 * Press the play button to compile. 

## Using
Point to the world, activate camera, select a filter and enjoy. I find specially useful the "red to blue" filter to catch up red corrections from black text.

Please take into consideration that, despite of using the autofocus feature of android camera (where available), this app produces somehow blurry images. When too blurry, you might be unable to see the difference between black an red with a single filter. In these situations, I use to move back and forth between filters and see what parts of the image change.

## Running the tests

This app can build its own icons. You can do so by uncommenting the createIcon lines at the end of setup(). You'd probably need changing Sketch permissions on Sketch Properties and/or AndroidManifest.xml in order to give write access to your external storage. Access to external Storage is needed for saving icons only.

## Built With

* [Processing](http://processing.org) - Simple to use graphics language. 
* [APDE](https://github.com/Calsign/APDE/wiki) - A processing IDE for Android.
* [Ketai](http://ketai.org/) - An easy-to-use android hardware library for processing.


## Authors

* **José G. Moya Y** - *Initial work* - [jgmy](https://github.com/jgmy)

See also the list of [contributors](https://github.com/jgmy/daltonaid/contributors) who participated in this project.

## License

This project is licensed under the GNU Free Software License. Sources can be acquired from github.

## Acknowledgments

* To Medialab-Prado, for introducing me to the processing language.
* This readme is based on https://gist.githubusercontent.com/PurpleBooth/109311bb0361f32d87a2/raw/8254b53ab8dcb18afc64287aaddd9e5b6059f880/README-Template.md
