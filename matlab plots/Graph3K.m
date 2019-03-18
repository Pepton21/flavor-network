removed = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35];
Ns = [11.546917168481356,9.804252280987502,8.721038786787865,8.056543521956128,7.698413095871361,7.355260015672292,6.991620651456062,6.673645125669261,6.410272338794963,6.1822383061356305,5.962025927124811,5.77957056323231,5.606832317135298,5.446134274003313,5.275116371957051,5.105382600984308,4.93584522377572,4.777301706338196,4.611931309682153,4.516174919161888,4.426848950247639,4.323964558008611,4.190964008871194,4.13032095161792,4.071674800039404,3.981409980816863,3.9182766131604234,3.838744495388981,3.742121282751236,3.6658028969611567,3.505457597699155,3.404782402081744,3.3458709734356478,3.2969023457193636,3.2451852765177294,3.2149058162827124];
scatter(removed, Ns, 'filled')

hold on;
plot(removed, Ns, 'Color', [0,0.4470,0.7410])
ylabel('Ns');
xlabel('Number of removed ingredients');
title('North American Cousine');
hold off;