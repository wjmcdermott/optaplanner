************************************************************************
file with basedata            : md341_.bas
initial value random generator: 343044162
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  22
horizon                       :  174
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     20      0       24       11       24
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3          10  11  17
   3        3          3           5   6  14
   4        3          1          14
   5        3          2           7  12
   6        3          1           9
   7        3          3           8  11  19
   8        3          2          13  18
   9        3          2          12  13
  10        3          3          12  15  19
  11        3          2          16  18
  12        3          1          21
  13        3          2          15  16
  14        3          3          15  16  21
  15        3          1          20
  16        3          1          20
  17        3          2          18  19
  18        3          2          20  21
  19        3          1          22
  20        3          1          22
  21        3          1          22
  22        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     3       4    8    4    0
         2     3       4    5    0    4
         3    10       4    1    0    4
  3      1     1       8    6    6    0
         2     7       7    6    4    0
         3     9       5    5    3    0
  4      1     1       3    6    0    6
         2     9       3    5    5    0
         3    10       2    5    0    4
  5      1     3       9    4    0    8
         2     4       8    4    0    4
         3     6       8    1    7    0
  6      1     7       6    9    9    0
         2     9       5    8    0    7
         3    10       5    7    0    6
  7      1     5       1    9    0    8
         2     8       1    5    0    8
         3     9       1    3    2    0
  8      1     3       9    6    0    5
         2     4       5    6    9    0
         3     9       3    6    3    0
  9      1     1      10    7    0   10
         2     3      10    6    0    7
         3     4       9    5    0    7
 10      1     2       3    8    0    9
         2     5       3    7    0    6
         3     6       2    7    0    6
 11      1     1       5    7    0    2
         2     6       3    4    6    0
         3     9       1    2    4    0
 12      1     1      10    6    0    8
         2     6      10    3    0    8
         3    10      10    2    0    7
 13      1     4       4    2    0    5
         2     7       4    2    8    0
         3    10       4    1    0    2
 14      1     1       7    9    7    0
         2     6       7    9    6    0
         3    10       5    8    4    0
 15      1     7       9   10   10    0
         2    10       8    7    4    0
         3    10       8    8    0   10
 16      1     6       4    6    0    5
         2    10       4    4    2    0
         3    10       4    5    1    0
 17      1     8       6    5    0    6
         2     9       4    3    8    0
         3    10       3    2    0    5
 18      1     3       9    6    8    0
         2     7       7    6    8    0
         3    10       6    6    7    0
 19      1     3       7    9    8    0
         2     8       6    7    5    0
         3     9       4    6    5    0
 20      1     1       2    5    4    0
         2     3       2    5    0    5
         3     4       2    4    0    2
 21      1     3       4   10    9    0
         2     8       3    9    9    0
         3     9       1    8    8    0
 22      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   15   14   91   79
************************************************************************