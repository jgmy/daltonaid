!/bin/sh
#
# Processing Android Mode does not use the manifest data supplied by APDE.
# Since most of my development takes place in APDE and I use
# desktop for exporting to gradle only, I will use a shell script to
# make manifest changes appear in gradle.

#  Permissions are less problematic since Processing an Gradle keep
#  permissions that appear inside AndroidManifest.

MANIFESTPACKAGE=$(grep "manifest.package" sketch.properties|cut -d= -f 2)
MANIFESTVERSIONNAME=$(grep "manifest.version.name" sketch.properties|cut -d= -f 2)
MANIFESTPACKAGE=$(grep "manifest.package" sketch.properties|cut -d= -f 2)
MANIFESTVERSIONCODE=$(grep "manifest.version.code" sketch.properties|cut -d= -f 2)


  cat android/app/build.gradle | sed -e "/defaultConfig/,/}/{;s/\(applicationId *[\"]\)[^\"]*\(\"\)/\1$MANIFESTPACKAGE\2/g;s/\(versionName[ ]*[\"]\)[^\"]*\(\"\)/\1$MANIFESTVERSIONNAME\2/g;s/\(versionCode[ ]*\)[0-9]*/\1$MANIFESTVERSIONCODE/g;}"  |less
