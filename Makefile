# all:
# 	javac -classpath "lib/lwjgl/*:src" ./src/Main.java; java -XstartOnFirstThread  -classpath "lib/lwjgl/*:src" Main

LIB_DIR = lib
SRC_DIR = src
GRAPHICS_DIR = $(SRC_DIR)/graphics

JAVA_SRC_FILES := $(wildcard $(SRC_DIR)/*.java)
JAVA_GRAPHICS_FILES := $(wildcard $(GRAPHICS_DIR)/*.java)
JAVA_LIB_FILES := $(wildcard $(LIB_DIR)/*/*.jar)

machin:
	@echo "$(JAVA_LIB_FILES)"
