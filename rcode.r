centroid = function ( X, Y ) {
cx = (X[1] + X[2] + X[3] + X[4]) / 4
cy = (Y[1] + Y[2] + Y[3] + Y[4]) / 4
data.frame( cx, cy )
}

show = function ( X, Y, TX=NULL, TY=NULL, title="" ) {
TL = c( "t1", "t2", "t3" )  #labels for tri  pts
QL = c( "p1", "p2", "p3", "p4" )  #labels for quad pts
width = max( abs( X[1] - X[2] ),
abs( X[1] - X[3] ),
abs( X[1] - X[4] ),
abs( X[2] - X[3] ),
abs( X[2] - X[4] ),
abs( X[3] - X[4] ),
abs( Y[1] - Y[2] ),
abs( Y[1] - Y[3] ),
abs( Y[1] - Y[4] ),
abs( Y[2] - Y[3] ),
abs( Y[2] - Y[4] ),
abs( Y[3] - Y[4] ) )
plot( TX, TY, asp=1, main=title, col="gray", pch=2, xlab="x", ylab="y" )
width = max( width, 
abs( TX[1] - TX[2] ),
abs( TX[1] - TX[3] ),
abs( TX[2] - TX[3] ),
abs( TY[1] - TY[2] ),
abs( TY[1] - TY[3] ),
abs( TY[2] - TY[3] ) )
text( TX+0.03*width, TY+0.02*width, labels=TL, col="gray" )
segments( TX[1], TY[1], TX[2], TY[2], col="gray", lty=2 )
segments( TX[2], TY[2], TX[3], TY[3], col="gray", lty=2 )
segments( TX[3], TY[3], TX[1], TY[1], col="gray", lty=2 )
points( X, Y, pch=15 )
text( X+0.03*width, Y+0.02*width, labels=QL )
segments( X[1], Y[1], X[2], Y[2] )
segments( X[2], Y[2], X[3], Y[3] )
segments( X[3], Y[3], X[4], Y[4] )
segments( X[4], Y[4], X[1], Y[1] )

#calc centroid
c = centroid( X, Y )
#calc limits
minX = X[1]
maxX = X[1]
minY = Y[1]
maxY = Y[1]
for (i in c(2, 3, 4)) {
	if (X[i] < minX)    minX = X[i]
	if (X[i] > maxX)    maxX = X[i]
	if (Y[i] < minY)    minY = Y[i]
	if (Y[i] > maxY)    maxY = Y[i]
}
#draw coordinate system centered at centroid
points( c, col="gray" )
segments( minX, c$cy, maxX, c$cy, col="gray", lty=3 )
segments( c$cx, minY, c$cx, maxY, col="gray", lty=3 )
}

X = c(0.2066666666666667, 0.21000000000000002, 0.22, 0.21000000000000002)
Y = c(-0.7806666666666665, -0.7827999999999999, -0.7732, -0.7747999999999999)
TX = c(0.2, 0.22, 0.2)
TY = c(-0.7924, -0.7732, -0.7764)
show( X, Y, TX, TY, title="Quad 1, Triangle 1")

X = c(0.2, 0.21000000000000002, 0.2066666666666667, 0.2)
Y = c(-0.7924, -0.7827999999999999, -0.7806666666666665, -0.7844)
TX = c(0.2, 0.22, 0.2)
TY = c(-0.7924, -0.7732, -0.7764)
show( X, Y, TX, TY, title="Quad 2, Triangle 1")

X = c(0.2, 0.2066666666666667, 0.21000000000000002, 0.2)
Y = c(-0.7844, -0.7806666666666665, -0.7747999999999999, -0.7764)
TX = c(0.2, 0.22, 0.2)
TY = c(-0.7924, -0.7732, -0.7764)
show( X, Y, TX, TY, title="Quad 3, Triangle 1")

X = c(0.2, 0.2672, 0.25146666666666667, 0.21000000000000002)
Y = c(-0.7924, -0.782, -0.7790666666666666, -0.7827999999999999)
TX = c(0.22, 0.2, 0.3344)
TY = c(-0.7732, -0.7924, -0.7716)
show( X, Y, TX, TY, title="Quad 4, Triangle 2")

X = c(0.21000000000000002, 0.25146666666666667, 0.2772, 0.22)
Y = c(-0.7827999999999999, -0.7790666666666666, -0.7724, -0.7732)
TX = c(0.22, 0.2, 0.3344)
TY = c(-0.7732, -0.7924, -0.7716)
show( X, Y, TX, TY, title="Quad 5, Triangle 2")

X = c(0.25146666666666667, 0.2672, 0.3344, 0.2772)
Y = c(-0.7790666666666666, -0.782, -0.7716, -0.7724)
TX = c(0.22, 0.2, 0.3344)
TY = c(-0.7732, -0.7924, -0.7716)
show( X, Y, TX, TY, title="Quad 6, Triangle 2")

X = c(0.3589333333333333, 0.3712, 0.3712, 0.3528)
Y = c(-0.7801333333333332, -0.7844, -0.7764, -0.774)
TX = c(0.3712, 0.3712, 0.3344)
TY = c(-0.7924, -0.7764, -0.7716)
show( X, Y, TX, TY, title="Quad 7, Triangle 3")

X = c(0.3528, 0.3712, 0.3712, 0.3589333333333333)
Y = c(-0.782, -0.7924, -0.7844, -0.7801333333333332)
TX = c(0.3712, 0.3712, 0.3344)
TY = c(-0.7924, -0.7764, -0.7716)
show( X, Y, TX, TY, title="Quad 8, Triangle 3")

X = c(0.3528, 0.3589333333333333, 0.3528, 0.3344)
Y = c(-0.782, -0.7801333333333332, -0.774, -0.7716)
TX = c(0.3712, 0.3712, 0.3344)
TY = c(-0.7924, -0.7764, -0.7716)
show( X, Y, TX, TY, title="Quad 9, Triangle 3")

X = c(0.2909333333333333, 0.3264, 0.3184, 0.2692)
Y = c(-0.7686666666666667, -0.7664, -0.7612, -0.7672)
TX = c(0.3344, 0.3184, 0.22)
TY = c(-0.7716, -0.7612, -0.7732)
show( X, Y, TX, TY, title="Quad 10, Triangle 4")

X = c(0.2772, 0.3344, 0.3264, 0.2909333333333333)
Y = c(-0.7724, -0.7716, -0.7664, -0.7686666666666667)
TX = c(0.3344, 0.3184, 0.22)
TY = c(-0.7716, -0.7612, -0.7732)
show( X, Y, TX, TY, title="Quad 11, Triangle 4")

X = c(0.22, 0.2772, 0.2909333333333333, 0.2692)
Y = c(-0.7732, -0.7724, -0.7686666666666667, -0.7672)
TX = c(0.3344, 0.3184, 0.22)
TY = c(-0.7716, -0.7612, -0.7732)
show( X, Y, TX, TY, title="Quad 12, Triangle 4")

X = c(0.30186666666666667, 0.3528, 0.3344, 0.2672)
Y = c(-0.7854666666666666, -0.782, -0.7716, -0.782)
TX = c(0.3712, 0.3344, 0.2)
TY = c(-0.7924, -0.7716, -0.7924)
show( X, Y, TX, TY, title="Quad 13, Triangle 5")

X = c(0.28559999999999997, 0.3712, 0.3528, 0.30186666666666667)
Y = c(-0.7924, -0.7924, -0.782, -0.7854666666666666)
TX = c(0.3712, 0.3344, 0.2)
TY = c(-0.7924, -0.7716, -0.7924)
show( X, Y, TX, TY, title="Quad 14, Triangle 5")

X = c(0.2, 0.28559999999999997, 0.30186666666666667, 0.2672)
Y = c(-0.7924, -0.7924, -0.7854666666666666, -0.782)
TX = c(0.3712, 0.3344, 0.2)
TY = c(-0.7924, -0.7716, -0.7924)
show( X, Y, TX, TY, title="Quad 15, Triangle 5")

X = c(0.26133333333333336, 0.2692, 0.3184, 0.28200000000000003)
Y = c(-0.7636, -0.7672, -0.7612, -0.7587999999999999)
TX = c(0.22, 0.3184, 0.2456)
TY = c(-0.7732, -0.7612, -0.7564)
show( X, Y, TX, TY, title="Quad 16, Triangle 6")

X = c(0.22, 0.2692, 0.26133333333333336, 0.2328)
Y = c(-0.7732, -0.7672, -0.7636, -0.7647999999999999)
TX = c(0.22, 0.3184, 0.2456)
TY = c(-0.7732, -0.7612, -0.7564)
show( X, Y, TX, TY, title="Quad 17, Triangle 6")

X = c(0.2328, 0.26133333333333336, 0.28200000000000003, 0.2456)
Y = c(-0.7647999999999999, -0.7636, -0.7587999999999999, -0.7564)
TX = c(0.22, 0.3184, 0.2456)
TY = c(-0.7732, -0.7612, -0.7564)
show( X, Y, TX, TY, title="Quad 18, Triangle 6")

X = c(0.2788, 0.312, 0.2948, 0.2784)
Y = c(-0.748, -0.7396, -0.7208, -0.7326666666666667)
TX = c(0.2456, 0.312, 0.2776)
TY = c(-0.7564, -0.7396, -0.702)
show( X, Y, TX, TY, title="Quad 19, Triangle 7")

X = c(0.2456, 0.2788, 0.2784, 0.2616)
Y = c(-0.7564, -0.748, -0.7326666666666667, -0.7292)
TX = c(0.2456, 0.312, 0.2776)
TY = c(-0.7564, -0.7396, -0.702)
show( X, Y, TX, TY, title="Quad 20, Triangle 7")

X = c(0.2616, 0.2784, 0.2948, 0.2776)
Y = c(-0.7292, -0.7326666666666667, -0.7208, -0.702)
TX = c(0.2456, 0.312, 0.2776)
TY = c(-0.7564, -0.7396, -0.702)
show( X, Y, TX, TY, title="Quad 21, Triangle 7")

X = c(0.2456, 0.28200000000000003, 0.292, 0.2788)
Y = c(-0.7564, -0.7587999999999999, -0.7524000000000001, -0.748)
TX = c(0.312, 0.2456, 0.3184)
TY = c(-0.7396, -0.7564, -0.7612)
show( X, Y, TX, TY, title="Quad 22, Triangle 8")

X = c(0.292, 0.31520000000000004, 0.312, 0.2788)
Y = c(-0.7524000000000001, -0.7504, -0.7396, -0.748)
TX = c(0.312, 0.2456, 0.3184)
TY = c(-0.7396, -0.7564, -0.7612)
show( X, Y, TX, TY, title="Quad 23, Triangle 8")

X = c(0.28200000000000003, 0.3184, 0.31520000000000004, 0.292)
Y = c(-0.7587999999999999, -0.7612, -0.7504, -0.7524000000000001)
TX = c(0.312, 0.2456, 0.3184)
TY = c(-0.7396, -0.7564, -0.7612)
show( X, Y, TX, TY, title="Quad 24, Triangle 8")

X = c(0.2948, 0.312, 0.3156, 0.30293333333333333)
Y = c(-0.7208, -0.7396, -0.7232000000000001, -0.7161333333333334)
TX = c(0.2776, 0.312, 0.3192)
TY = c(-0.702, -0.7396, -0.7068)
show( X, Y, TX, TY, title="Quad 25, Triangle 9")

X = c(0.2948, 0.30293333333333333, 0.2984, 0.2776)
Y = c(-0.7208, -0.7161333333333334, -0.7043999999999999, -0.702)
TX = c(0.2776, 0.312, 0.3192)
TY = c(-0.702, -0.7396, -0.7068)
show( X, Y, TX, TY, title="Quad 26, Triangle 9")

X = c(0.30293333333333333, 0.3156, 0.3192, 0.2984)
Y = c(-0.7161333333333334, -0.7232000000000001, -0.7068, -0.7043999999999999)
TX = c(0.2776, 0.312, 0.3192)
TY = c(-0.702, -0.7396, -0.7068)
show( X, Y, TX, TY, title="Quad 27, Triangle 9")

X = c(0.2776, 0.326, 0.3802666666666667, 0.3832)
Y = c(-0.702, -0.6359999999999999, -0.4932, -0.4548)
TX = c(0.4888, 0.2776, 0.3744)
TY = c(-0.2076, -0.702, -0.57)
show( X, Y, TX, TY, title="Quad 28, Triangle 10")

X = c(0.3802666666666667, 0.4316, 0.4888, 0.3832)
Y = c(-0.4932, -0.3888, -0.2076, -0.4548)
TX = c(0.4888, 0.2776, 0.3744)
TY = c(-0.2076, -0.702, -0.57)
show( X, Y, TX, TY, title="Quad 29, Triangle 10")

X = c(0.326, 0.3744, 0.4316, 0.3802666666666667)
Y = c(-0.6359999999999999, -0.57, -0.3888, -0.4932)
TX = c(0.4888, 0.2776, 0.3744)
TY = c(-0.2076, -0.702, -0.57)
show( X, Y, TX, TY, title="Quad 30, Triangle 10")

X = c(0.326, 0.33760000000000007, 0.36760000000000004, 0.3744)
Y = c(-0.6359999999999999, -0.6254666666666666, -0.5871999999999999, -0.57)
TX = c(0.3608, 0.3744, 0.2776)
TY = c(-0.6044, -0.57, -0.702)
show( X, Y, TX, TY, title="Quad 31, Triangle 11")

X = c(0.31920000000000004, 0.3608, 0.36760000000000004, 0.33760000000000007)
Y = c(-0.6532, -0.6044, -0.5871999999999999, -0.6254666666666666)
TX = c(0.3608, 0.3744, 0.2776)
TY = c(-0.6044, -0.57, -0.702)
show( X, Y, TX, TY, title="Quad 32, Triangle 11")

X = c(0.2776, 0.31920000000000004, 0.33760000000000007, 0.326)
Y = c(-0.702, -0.6532, -0.6254666666666666, -0.6359999999999999)
TX = c(0.3608, 0.3744, 0.2776)
TY = c(-0.6044, -0.57, -0.702)
show( X, Y, TX, TY, title="Quad 33, Triangle 11")

X = c(0.3608, 0.47400000000000003, 0.4408, 0.36760000000000004)
Y = c(-0.6044, -0.6044, -0.5929333333333333, -0.5871999999999999)
TX = c(0.3744, 0.3608, 0.5872)
TY = c(-0.57, -0.6044, -0.6044)
show( X, Y, TX, TY, title="Quad 34, Triangle 12")

X = c(0.36760000000000004, 0.4408, 0.4808, 0.3744)
Y = c(-0.5871999999999999, -0.5929333333333333, -0.5871999999999999, -0.57)
TX = c(0.3744, 0.3608, 0.5872)
TY = c(-0.57, -0.6044, -0.6044)
show( X, Y, TX, TY, title="Quad 35, Triangle 12")

X = c(0.47400000000000003, 0.5872, 0.4808, 0.4408)
Y = c(-0.6044, -0.6044, -0.5871999999999999, -0.5929333333333333)
TX = c(0.3744, 0.3608, 0.5872)
TY = c(-0.57, -0.6044, -0.6044)
show( X, Y, TX, TY, title="Quad 36, Triangle 12")

X = c(0.2776, 0.2984, 0.3192, 0.31920000000000004)
Y = c(-0.702, -0.7043999999999999, -0.6710666666666666, -0.6532)
TX = c(0.3608, 0.2776, 0.3192)
TY = c(-0.6044, -0.702, -0.7068)
show( X, Y, TX, TY, title="Quad 37, Triangle 13")

X = c(0.3192, 0.33999999999999997, 0.3608, 0.31920000000000004)
Y = c(-0.6710666666666666, -0.6556, -0.6044, -0.6532)
TX = c(0.3608, 0.2776, 0.3192)
TY = c(-0.6044, -0.702, -0.7068)
show( X, Y, TX, TY, title="Quad 38, Triangle 13")

X = c(0.2984, 0.3192, 0.33999999999999997, 0.3192)
Y = c(-0.7043999999999999, -0.7068, -0.6556, -0.6710666666666666)
TX = c(0.3608, 0.2776, 0.3192)
TY = c(-0.6044, -0.702, -0.7068)
show( X, Y, TX, TY, title="Quad 39, Triangle 13")

X = c(0.3744, 0.42400000000000004, 0.4456, 0.4316)
Y = c(-0.57, -0.45039999999999997, -0.36946666666666667, -0.3888)
TX = c(0.4888, 0.3744, 0.4736)
TY = c(-0.2076, -0.57, -0.3308)
show( X, Y, TX, TY, title="Quad 40, Triangle 14")

X = c(0.4316, 0.4456, 0.4812, 0.4888)
Y = c(-0.3888, -0.36946666666666667, -0.2692, -0.2076)
TX = c(0.4888, 0.3744, 0.4736)
TY = c(-0.2076, -0.57, -0.3308)
show( X, Y, TX, TY, title="Quad 41, Triangle 14")

X = c(0.42400000000000004, 0.4736, 0.4812, 0.4456)
Y = c(-0.45039999999999997, -0.3308, -0.2692, -0.36946666666666667)
TX = c(0.4888, 0.3744, 0.4736)
TY = c(-0.2076, -0.57, -0.3308)
show( X, Y, TX, TY, title="Quad 42, Triangle 14")

X = c(0.5933333333333334, 0.6004, 0.6216, 0.6004)
Y = c(-0.7801333333333332, -0.782, -0.7716, -0.774)
TX = c(0.5792, 0.6216, 0.5792)
TY = c(-0.7924, -0.7716, -0.7764)
show( X, Y, TX, TY, title="Quad 43, Triangle 15")

X = c(0.5792, 0.6004, 0.5933333333333334, 0.5792)
Y = c(-0.7924, -0.782, -0.7801333333333332, -0.7844)
TX = c(0.5792, 0.6216, 0.5792)
TY = c(-0.7924, -0.7716, -0.7764)
show( X, Y, TX, TY, title="Quad 44, Triangle 15")

X = c(0.5792, 0.5933333333333334, 0.6004, 0.5792)
Y = c(-0.7844, -0.7801333333333332, -0.774, -0.7764)
TX = c(0.5792, 0.6216, 0.5792)
TY = c(-0.7924, -0.7716, -0.7764)
show( X, Y, TX, TY, title="Quad 45, Triangle 15")

X = c(0.5792, 0.6896, 0.6669333333333333, 0.6004)
Y = c(-0.7924, -0.7924, -0.7854666666666666, -0.782)
TX = c(0.6216, 0.5792, 0.8)
TY = c(-0.7716, -0.7924, -0.7924)
show( X, Y, TX, TY, title="Quad 46, Triangle 16")

X = c(0.6004, 0.6669333333333333, 0.7108000000000001, 0.6216)
Y = c(-0.782, -0.7854666666666666, -0.782, -0.7716)
TX = c(0.6216, 0.5792, 0.8)
TY = c(-0.7716, -0.7924, -0.7924)
show( X, Y, TX, TY, title="Quad 47, Triangle 16")

X = c(0.6896, 0.8, 0.7108000000000001, 0.6669333333333333)
Y = c(-0.7924, -0.7924, -0.782, -0.7854666666666666)
TX = c(0.6216, 0.5792, 0.8)
TY = c(-0.7716, -0.7924, -0.7924)
show( X, Y, TX, TY, title="Quad 48, Triangle 16")

X = c(0.7914666666666667, 0.8, 0.8, 0.7872)
Y = c(-0.7803999999999999, -0.7844, -0.7764, -0.7744)
TX = c(0.8, 0.8, 0.7744)
TY = c(-0.7924, -0.7764, -0.7724)
show( X, Y, TX, TY, title="Quad 49, Triangle 17")

X = c(0.7872, 0.8, 0.8, 0.7914666666666667)
Y = c(-0.7824, -0.7924, -0.7844, -0.7803999999999999)
TX = c(0.8, 0.8, 0.7744)
TY = c(-0.7924, -0.7764, -0.7724)
show( X, Y, TX, TY, title="Quad 50, Triangle 17")

X = c(0.7872, 0.7914666666666667, 0.7872, 0.7744)
Y = c(-0.7824, -0.7803999999999999, -0.7744, -0.7724)
TX = c(0.8, 0.8, 0.7744)
TY = c(-0.7924, -0.7764, -0.7724)
show( X, Y, TX, TY, title="Quad 51, Triangle 17")

X = c(0.6216, 0.698, 0.7173333333333334, 0.6888000000000001)
Y = c(-0.7716, -0.772, -0.7684000000000001, -0.7664)
TX = c(0.756, 0.6216, 0.7744)
TY = c(-0.7612, -0.7716, -0.7724)
show( X, Y, TX, TY, title="Quad 52, Triangle 18")

X = c(0.7173333333333334, 0.7652, 0.756, 0.6888000000000001)
Y = c(-0.7684000000000001, -0.7667999999999999, -0.7612, -0.7664)
TX = c(0.756, 0.6216, 0.7744)
TY = c(-0.7612, -0.7716, -0.7724)
show( X, Y, TX, TY, title="Quad 53, Triangle 18")

X = c(0.698, 0.7744, 0.7652, 0.7173333333333334)
Y = c(-0.772, -0.7724, -0.7667999999999999, -0.7684000000000001)
TX = c(0.756, 0.6216, 0.7744)
TY = c(-0.7612, -0.7716, -0.7724)
show( X, Y, TX, TY, title="Quad 54, Triangle 18")

X = c(0.6276, 0.6704, 0.6948000000000001, 0.6336)
Y = c(-0.7672, -0.7652, -0.762, -0.7628)
TX = c(0.756, 0.6336, 0.6216)
TY = c(-0.7612, -0.7628, -0.7716)
show( X, Y, TX, TY, title="Quad 55, Triangle 19")

X = c(0.6704, 0.6888000000000001, 0.756, 0.6948000000000001)
Y = c(-0.7652, -0.7664, -0.7612, -0.762)
TX = c(0.756, 0.6336, 0.6216)
TY = c(-0.7612, -0.7628, -0.7716)
show( X, Y, TX, TY, title="Quad 56, Triangle 19")

X = c(0.6216, 0.6888000000000001, 0.6704, 0.6276)
Y = c(-0.7716, -0.7664, -0.7652, -0.7672)
TX = c(0.756, 0.6336, 0.6216)
TY = c(-0.7612, -0.7628, -0.7716)
show( X, Y, TX, TY, title="Quad 57, Triangle 19")

X = c(0.7108000000000001, 0.7319999999999999, 0.698, 0.6216)
Y = c(-0.782, -0.7788, -0.772, -0.7716)
TX = c(0.7744, 0.6216, 0.8)
TY = c(-0.7724, -0.7716, -0.7924)
show( X, Y, TX, TY, title="Quad 58, Triangle 20")

X = c(0.7319999999999999, 0.7872, 0.7744, 0.698)
Y = c(-0.7788, -0.7824, -0.7724, -0.772)
TX = c(0.7744, 0.6216, 0.8)
TY = c(-0.7724, -0.7716, -0.7924)
show( X, Y, TX, TY, title="Quad 59, Triangle 20")

X = c(0.7108000000000001, 0.8, 0.7872, 0.7319999999999999)
Y = c(-0.782, -0.7924, -0.7824, -0.7788)
TX = c(0.7744, 0.6216, 0.8)
TY = c(-0.7724, -0.7716, -0.7924)
show( X, Y, TX, TY, title="Quad 60, Triangle 20")

X = c(0.5744, 0.6576, 0.6066666666666668, 0.5396000000000001)
Y = c(-0.57, -0.6548, -0.5057333333333333, -0.3888)
TX = c(0.5048, 0.5744, 0.7408)
TY = c(-0.2076, -0.57, -0.7396)
show( X, Y, TX, TY, title="Quad 61, Triangle 21")

X = c(0.6066666666666668, 0.6228, 0.5048, 0.5396000000000001)
Y = c(-0.5057333333333333, -0.4736, -0.2076, -0.3888)
TX = c(0.5048, 0.5744, 0.7408)
TY = c(-0.2076, -0.57, -0.7396)
show( X, Y, TX, TY, title="Quad 62, Triangle 21")

X = c(0.6576, 0.7408, 0.6228, 0.6066666666666668)
Y = c(-0.6548, -0.7396, -0.4736, -0.5057333333333333)
TX = c(0.5048, 0.5744, 0.7408)
TY = c(-0.2076, -0.57, -0.7396)
show( X, Y, TX, TY, title="Quad 63, Triangle 21")

X = c(0.4808, 0.5872, 0.5808, 0.512)
Y = c(-0.5871999999999999, -0.6044, -0.5871999999999999, -0.5814666666666666)
TX = c(0.3744, 0.5872, 0.5744)
TY = c(-0.57, -0.6044, -0.57)
show( X, Y, TX, TY, title="Quad 64, Triangle 22")

X = c(0.4808, 0.512, 0.47440000000000004, 0.3744)
Y = c(-0.5871999999999999, -0.5814666666666666, -0.57, -0.57)
TX = c(0.3744, 0.5872, 0.5744)
TY = c(-0.57, -0.6044, -0.57)
show( X, Y, TX, TY, title="Quad 65, Triangle 22")

X = c(0.512, 0.5808, 0.5744, 0.47440000000000004)
Y = c(-0.5814666666666666, -0.5871999999999999, -0.57, -0.57)
TX = c(0.3744, 0.5872, 0.5744)
TY = c(-0.57, -0.6044, -0.57)
show( X, Y, TX, TY, title="Quad 66, Triangle 22")

X = c(0.4736, 0.4892, 0.4890666666666667, 0.4812)
Y = c(-0.3308, -0.2692, -0.24866666666666667, -0.2692)
TX = c(0.4888, 0.4736, 0.5048)
TY = c(-0.2076, -0.3308, -0.2076)
show( X, Y, TX, TY, title="Quad 67, Triangle 23")

X = c(0.4812, 0.4890666666666667, 0.4968, 0.4888)
Y = c(-0.2692, -0.24866666666666667, -0.2076, -0.2076)
TX = c(0.4888, 0.4736, 0.5048)
TY = c(-0.2076, -0.3308, -0.2076)
show( X, Y, TX, TY, title="Quad 68, Triangle 23")

X = c(0.4890666666666667, 0.4892, 0.5048, 0.4968)
Y = c(-0.24866666666666667, -0.2692, -0.2076, -0.2076)
TX = c(0.4888, 0.4736, 0.5048)
TY = c(-0.2076, -0.3308, -0.2076)
show( X, Y, TX, TY, title="Quad 69, Triangle 23")

X = c(0.5872, 0.664, 0.6341333333333333, 0.5808)
Y = c(-0.6044, -0.672, -0.638, -0.5871999999999999)
TX = c(0.5744, 0.5872, 0.7408)
TY = c(-0.57, -0.6044, -0.7396)
show( X, Y, TX, TY, title="Quad 70, Triangle 24")

X = c(0.6341333333333333, 0.6576, 0.5744, 0.5808)
Y = c(-0.638, -0.6548, -0.57, -0.5871999999999999)
TX = c(0.5744, 0.5872, 0.7408)
TY = c(-0.57, -0.6044, -0.7396)
show( X, Y, TX, TY, title="Quad 71, Triangle 24")

X = c(0.664, 0.7408, 0.6576, 0.6341333333333333)
Y = c(-0.672, -0.7396, -0.6548, -0.638)
TX = c(0.5744, 0.5872, 0.7408)
TY = c(-0.57, -0.6044, -0.7396)
show( X, Y, TX, TY, title="Quad 72, Triangle 24")

X = c(0.524, 0.5744, 0.5396000000000001, 0.5176)
Y = c(-0.45039999999999997, -0.57, -0.3888, -0.36946666666666667)
TX = c(0.4736, 0.5744, 0.5048)
TY = c(-0.3308, -0.57, -0.2076)
show( X, Y, TX, TY, title="Quad 73, Triangle 25")

X = c(0.4736, 0.524, 0.5176, 0.4892)
Y = c(-0.3308, -0.45039999999999997, -0.36946666666666667, -0.2692)
TX = c(0.4736, 0.5744, 0.5048)
TY = c(-0.3308, -0.57, -0.2076)
show( X, Y, TX, TY, title="Quad 74, Triangle 25")

X = c(0.5176, 0.5396000000000001, 0.5048, 0.4892)
Y = c(-0.36946666666666667, -0.3888, -0.2076, -0.2692)
TX = c(0.4736, 0.5744, 0.5048)
TY = c(-0.3308, -0.57, -0.2076)
show( X, Y, TX, TY, title="Quad 75, Triangle 25")

X = c(0.6712000000000001, 0.6872, 0.7408, 0.69)
Y = c(-0.7489333333333335, -0.7512000000000001, -0.7396, -0.742)
TX = c(0.6336, 0.7408, 0.6392)
TY = c(-0.7628, -0.7396, -0.7444)
show( X, Y, TX, TY, title="Quad 76, Triangle 26")

X = c(0.6336, 0.6872, 0.6712000000000001, 0.6364000000000001)
Y = c(-0.7628, -0.7512000000000001, -0.7489333333333335, -0.7536)
TX = c(0.6336, 0.7408, 0.6392)
TY = c(-0.7628, -0.7396, -0.7444)
show( X, Y, TX, TY, title="Quad 77, Triangle 26")

X = c(0.6364000000000001, 0.6712000000000001, 0.69, 0.6392)
Y = c(-0.7536, -0.7489333333333335, -0.742, -0.7444)
TX = c(0.6336, 0.7408, 0.6392)
TY = c(-0.7628, -0.7396, -0.7444)
show( X, Y, TX, TY, title="Quad 78, Triangle 26")

X = c(0.6336, 0.6948000000000001, 0.7101333333333333, 0.6872)
Y = c(-0.7628, -0.762, -0.7545333333333334, -0.7512000000000001)
TX = c(0.7408, 0.6336, 0.756)
TY = c(-0.7396, -0.7628, -0.7612)
show( X, Y, TX, TY, title="Quad 79, Triangle 27")

X = c(0.7101333333333333, 0.7484, 0.7408, 0.6872)
Y = c(-0.7545333333333334, -0.7504, -0.7396, -0.7512000000000001)
TX = c(0.7408, 0.6336, 0.756)
TY = c(-0.7396, -0.7628, -0.7612)
show( X, Y, TX, TY, title="Quad 80, Triangle 27")

X = c(0.6948000000000001, 0.756, 0.7484, 0.7101333333333333)
Y = c(-0.762, -0.7612, -0.7504, -0.7545333333333334)
TX = c(0.7408, 0.6336, 0.756)
TY = c(-0.7396, -0.7628, -0.7612)
show( X, Y, TX, TY, title="Quad 81, Triangle 27")

X = c(0.6808000000000001, 0.7408, 0.664, 0.6496000000000001)
Y = c(-0.712, -0.7396, -0.672, -0.6761333333333334)
TX = c(0.6208, 0.7408, 0.5872)
TY = c(-0.6844, -0.7396, -0.6044)
show( X, Y, TX, TY, title="Quad 82, Triangle 28")

X = c(0.6208, 0.6808000000000001, 0.6496000000000001, 0.6040000000000001)
Y = c(-0.6844, -0.712, -0.6761333333333334, -0.6444000000000001)
TX = c(0.6208, 0.7408, 0.5872)
TY = c(-0.6844, -0.7396, -0.6044)
show( X, Y, TX, TY, title="Quad 83, Triangle 28")

X = c(0.6496000000000001, 0.664, 0.5872, 0.6040000000000001)
Y = c(-0.6761333333333334, -0.672, -0.6044, -0.6444000000000001)
TX = c(0.6208, 0.7408, 0.5872)
TY = c(-0.6844, -0.7396, -0.6044)
show( X, Y, TX, TY, title="Quad 84, Triangle 28")

X = c(0.63, 0.6669333333333333, 0.6808000000000001, 0.6208)
Y = c(-0.7143999999999999, -0.7228, -0.712, -0.6844)
TX = c(0.7408, 0.6208, 0.6392)
TY = c(-0.7396, -0.6844, -0.7444)
show( X, Y, TX, TY, title="Quad 85, Triangle 29")

X = c(0.69, 0.7408, 0.6808000000000001, 0.6669333333333333)
Y = c(-0.742, -0.7396, -0.712, -0.7228)
TX = c(0.7408, 0.6208, 0.6392)
TY = c(-0.7396, -0.6844, -0.7444)
show( X, Y, TX, TY, title="Quad 86, Triangle 29")

X = c(0.6392, 0.69, 0.6669333333333333, 0.63)
Y = c(-0.7444, -0.742, -0.7228, -0.7143999999999999)
TX = c(0.7408, 0.6208, 0.6392)
TY = c(-0.7396, -0.6844, -0.7444)
show( X, Y, TX, TY, title="Quad 87, Triangle 29")


