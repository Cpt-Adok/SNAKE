all:
	javac -classpath "lib/lwjgl/*:src" ./src/Main.java; java -XstartOnFirstThread  -classpath "lib/lwjgl/*:src" Main