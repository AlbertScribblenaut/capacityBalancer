classdef combination
    %COMBINATION Summary of this class goes here
    %   Detailed explanation goes here
    
    properties
        an % instance of sample class that is an anode
        cath % instance of sample class that is a cathode
        ratio % gives a ratio of anode capacity to cathode capacity
    end
    
    methods
        function obj = combination(an,cath)
            %COMBINATION Construct an instance of this class
            %   Detailed explanation goes here
            obj.an = an;
            obj.cath = cath;
            obj.ratio = getRatio(an, cath);
        end
    end
end

