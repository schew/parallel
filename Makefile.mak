C++ = g++
FMP = -fopenmp
CFLAGS = -c -g -fopenmp


all: filter optimized_filter optimized_filter_notbest

filter: filter.o
	$(C++) $(FMP) -o filter filter.o

filter_optimized: filter_optimized.o
	$(C++) $(FMP) -o filter_optimized filter_optimized.o

clean:
	rm -f *.o

%.o:	%.cpp
	$(C++) $(CFLAGS) $*.cpp

