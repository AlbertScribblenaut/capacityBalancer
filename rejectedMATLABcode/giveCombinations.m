%% run this script with a spreadsheet in a directory
[num, raw] = xlsread('test.xlsx');
numSamples = raw.size(1) - 1;
pq = java.util.PriorityQueue;

