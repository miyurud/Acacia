C = gcc
CC = g++
SOURCE = cpp/src
OUTDIR = bin
THIRD_PARTY_INCLUDES=/nfs/data0/miyuru/software/boost_1_48_0/include
THIRD_PARTY_LIBS=/nfs/data0/miyuru/software/boost_1_48_0/lib


all:
	$(CC) $(SOURCE)/NotInVertsSearch.cpp -o $(OUTDIR)/NotInVertsSearch -I $(THIRD_PARTY_INCLUDES) -L $(THIRD_PARTY_LIBS) -lboost_system -lboost_filesystem

clean:
	rm -rf *.o
