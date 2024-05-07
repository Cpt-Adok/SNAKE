# Paramètres
.PHONY: all clean run BIN_DIR
.SILENT: clean run 

# variables
JAVAC = javac
JAVA = java

MAIN_FILE = Main
BIN_DIR = bin
SRC_DIR = src
LIB_DIR = lib

JAR = $(LIB_DIR)/*:$(LIB_DIR)/lwjgl/*

JAR_JAVAC = $(JAR):$(SRC_DIR)
JAR_JAVA = $(JAR):$(BIN_DIR)

UNAME := $(shell uname)

# main
all: $(MAIN_FILE) run

$(MAIN_FILE) : $(BIN_DIR)/$(MAIN_FILE).class

$(BIN_DIR)/$(MAIN_FILE).class : $(SRC_DIR)/$(MAIN_FILE).java
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -sourcepath $(SRC_DIR) -classpath $(JAR_JAVAC) $<

run:
	java -cp $(BIN_DIR) -XstartOnFirstThread -classpath $(JAR_JAVA) $(MAIN_FILE)

clean:
	@rm -rf $(BIN_DIR)