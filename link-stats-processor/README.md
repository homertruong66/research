Default config: java -jar <jar-file> "data-processor".
Ex: java -jar data-processor-1.0.0-SNAPSHOT-shaded.jar "data-processor"

Custom config: java -jar -DCONFIG_OVERRIDE="<path>/*.properties" <jar-file> "data-processor".
Ex: java -jar -DCONFIG_OVERRIDE="D:\link-stats-processor\config\profiles\dev-it\data-processor.properties" data-processor-1.0.0-SNAPSHOT-shaded.jar "data-processor"
