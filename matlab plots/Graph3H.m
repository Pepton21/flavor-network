Ns = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,84,86,87,88,94,97,99,102,127,128];
Pr = [0.21650008470269355,0.2470796460176991,0.20820385332504662,0.24840182648401826,0.23529411764705882,0.28781512605042014,0.234375,0.2434017595307918,0.262350936967632,0.2542087542087542,0.32217573221757323,0.2717678100263852,0.25257731958762886,0.23780487804878048,0.3057553956834532,0.2745762711864407,0.2977941176470588,0.25,0.23376623376623376,0.2422680412371134,0.2564102564102564,0.21084337349397592,0.25157232704402516,0.2838709677419355,0.3364485981308411,0.28402366863905326,0.2916666666666667,0.28368794326241137,0.1830065359477124,0.27102803738317754,0.23076923076923078,0.2857142857142857,0.21,0.23484848484848486,0.13924050632911392,0.3516483516483517,0.3829787234042553,0.11764705882352941,0.27956989247311825,0.203125,0.3253012048192771,0.2692307692307692,0.25675675675675674,0.3409090909090909,0.23684210526315788,0.19642857142857142,0.05555555555555555,0.417910447761194,0.1935483870967742,0.2777777777777778,0.23076923076923078,0.28205128205128205,0.08928571428571429,0.3170731707317073,0.2,0.35294117647058826,0.10714285714285714,0.16666666666666666,0.2,0.16666666666666666,0.0625,0.2916666666666667,0.23076923076923078,0.13636363636363635,0.30434782608695654,0.2571428571428571,0.05,0.3,0.47058823529411764,0.15,0.15,0.2222222222222222,0.25,0.25,0.21428571428571427,0.3333333333333333,0.25,0,0.07692307692307693,0.21428571428571427,0.2857142857142857,0.1111111111111111,0,0.3,0.27586206896551724,0,0.14285714285714285,0.16666666666666666,0.125,0.25,0.125,0.1111111111111111,0.3333333333333333];

scatter (Ns, Pr, 'r')
ylim([0 1])
h = lsline;
set(h(1),'color',[0,0.4470,0.7410])
legend({'East Asian', 'Linear Regression Line'}, 'Location', 'northwest');