removed = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35];
Ns = [6.142004136525209,6.900148197675401,7.703597490343449,8.28887928145619,9.040233327475416,10.027387041968193,10.57194294841758,11.210437133026904,11.680057503705731,12.55121799260696,12.773317512646361,13.02939037865156,14.262570711234753,14.62546892413297,15.073136587326106,15.394544297627125,15.875110789175123,16.223522248116055,16.46453587275066,16.764223713660645,17.09730931008643,17.30734402943643,17.464946875876148,17.62494743794677,18.711274654608392,19.401595087242853,19.437533693046106,20.283059676042246,20.560046962464238,20.70845127400549,20.756221974642433,20.8618869624818,21.19632135991382,21.298437292901564,21.349288761408285,21.387047490647905];
scatter(removed, Ns, 'filled', 'r')

hold on;
plot(removed, Ns, 'Color', 'r')
ylabel('Ns');
xlabel('Number of removed ingredients');
title('East Asian Cousine');
hold off;