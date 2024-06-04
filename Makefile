# Paramètres
.PHONY: all clean run
.SILENT: run clean

# variables
JAVAC = javac
JAVA = java

MAIN_FILE = Main
BIN_DIR = bin
SRC_DIR = src
LIB_DIR = lib

JAR = $(LIB_DIR)/*

#Arguments pour java
channel = $(arg1)
adversaire = $(arg2)

# main
all: clean $(MAIN_FILE) run 

$(MAIN_FILE) : $(BIN_DIR)/$(MAIN_FILE).class

$(BIN_DIR)/$(MAIN_FILE).class : $(SRC_DIR)/$(MAIN_FILE).java
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -sourcepath $(SRC_DIR) -classpath $(JAR) $<

run:
	java -Xmx16g -cp $(BIN_DIR) $(MAIN_FILE) $(channel) $(adversaire)

clean:
	@rm -rf $(BIN_DIR)