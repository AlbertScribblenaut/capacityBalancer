function priority = assignPriority(combo)
%% priority assigned to a combination in the priority queue

idealRatio = 1.08;
priority = abs(combo.ratio - idealRatio);

end