C++ = g++
FMP = -fopenmp
CFLAGS = -c -g -fopenmp


all: filter filter_optimized

filter: filter.o
	$(C++) $(FMP) -o filter filter.o

filter_optimized: filter_optimized.o
	$(C++) $(FMP) -o filter_optimized filter_optimized.o

clean:
	rm -f *.o

%.o:	%.cpp
	$(C++) $(CFLAGS) $*.cpp

