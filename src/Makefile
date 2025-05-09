JAVAC       := javac
SRC_DIR     := .
JAVA        := java
SRC_FILES   := SnakeGame.java GamePanel.java GameFrame.java
CLASS_FILES := $(SRC_FILES:.java=.class)
main := SnakeGame

.PHONY: build run clean

build: $(CLASS_FILES)

%.class: %.java
	$(JAVAC) $<

run: build
	$(JAVA) $(main)

clean:
	rm -f *.class '.*.swp'

