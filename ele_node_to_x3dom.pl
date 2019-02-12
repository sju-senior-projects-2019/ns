#
#convert a pair of .ele and .node files (representing triangles) to an .html
#  x3dom file of triangles (as a wire frame & filled in triangles).
#  example:  The triangle program runs on a.poly and creates a.1.ele and a.1.node.
#            perl ele_node_to_x3dom.pl  a.1 > a.1.wire.html
#            note:  can't use input redirection (<) because there are 2 input files (.ele and .node)!
#  author :  george j. grevera, ph.d. (ggrevera@sju.edu)
#
use strict;

#parse command line
my $midpoints = 1;
my $verbose   = 0;   #optional
my $prefix    = "";  #required (prefix of _two_ input files)

if ($#ARGV == 0) {
    $prefix = $ARGV[0];
} elsif ($#ARGV == 1) {
    if ($ARGV[0] eq "-v")  {  $verbose = 1;  }
    else                   {  usage();       }
    $prefix = $ARGV[1];
} else {
    usage();
}

my $ele  = $prefix . ".ele";
if (!-e $ele) {
    print STDERR ".ele file, $ele, does not exist. \n";
    usage();
}

my $node = $prefix . ".node";
if (!-e $node) {
    print STDERR ".node file, $node, does not exist. \n";
    usage();
}

#read and store the node (vertex) file
if ($verbose) { print STDERR "Loading $node. \n"; }
open( my $NODE, $node ) || die "couldn't open the file, $node!";

#read the header
#  <# of vertices> <dimension (must be 2)> <# of attributes> <# of boundary markers (0 or 1)>
skip( $NODE );
my ($vCount,$dim,$attrCount,$bnd) = split( /\s+/ );
die "file format variation is not supported!"  unless $vCount >  0;
die "dimension other than 2 is not supported!" unless $dim    == 2;

#load vertices (numbering may start with either 0 or 1)
#  <vertex #> <x> <y> [attributes] [boundary marker]
my $startsAtZero = 0;
my @vertices;
for (my $i=0; $i<$vCount; $i++) {
    skip( $NODE );
    my ($v,$x,$y,$a,$b) = split( /\s+/ );
    $vertices[ $v ] = $x . " " . $y . " " . $a . " " . $b;
    if ($i == 0) {
        if ($v == 0) {
            $startsAtZero = 1;
        }
    }
}

close( $NODE );
if ($verbose) { print STDERR "Done. \n"; }

open( my $ELE, $ele ) || die "couldn't open the file, $ele!";

#read the header
#  <# of triangles> <nodes per triangle> <# of attributes>
skip( $ELE );
my ($nt,$npt,$noa) = split( /\s+/ );
die "bad nodes per triangle, $npt! \n" unless $npt == 3;

header();    #output file header

my $pos = tell( $ELE );  #remember pos of first triangle
outputFilledTriangles();

seek( $ELE, $pos, 0 ) || die "seek failed.";  #move to first triangle
outputWireFrame();

seek( $ELE, $pos, 0 ) || die "seek failed.";  #move to first triangle
outputCentroids();

footer();    #output file footer

close( ELE );

exit;
#------------------------------------------------------------------------------
sub outputCentroids {
    print << "EOT";
        <shape>
            <appearance> <material diffuseColor ='0 1 0' emissiveColor='0 1 0'></material> </appearance> 
            <pointset solid="false"> <coordinate point="
EOT

    for (my $i=0; $i<$nt; $i++) {
        skip( $ELE );
        #<triangle #> <node> <node> <node> ... [attributes]
        my ($t,$n1,$n2,$n3) = split( /\s+/ );
        my ($x1,$y1) = split( /\s+/, $vertices[ $n1 ] );
        my ($x2,$y2) = split( /\s+/, $vertices[ $n2 ] );
        my ($x3,$y3) = split( /\s+/, $vertices[ $n3 ] );

        my $x = ($x1 + $x2 + $x3) / 3;
        my $y = ($y1 + $y2 + $y3) / 3;
        print "$x $y 0 \n";

        if ($midpoints) {
           my $m12x = ($x1 + $x2) / 2;
           my $m12y = ($y1 + $y2) / 2;

           my $m23x = ($x2 + $x3) / 2;
           my $m23y = ($y2 + $y3) / 2;

           my $m31x = ($x3 + $x1) / 2;
           my $m31y = ($y3 + $y1) / 2;

           print "$m12x $m12y 0 \n";
           print "$m23x $m23y 0 \n";
           print "$m31x $m31y 0 \n";
        }
    }

    print << "EOT";
        " /> </pointset> </shape>
EOT
}
#------------------------------------------------------------------------------
#output filled in triangles
sub outputFilledTriangles {
    print << "EOT";
                <shape>
                    <appearance> <Material diffuseColor='0 0 0.5' emissiveColor='0 0.1 0' shininess='0.8' specularColor='0.3 0 0'/> </appearance>
                    <TriangleSet solid='false' ccw='true' colorPerVertex='true' normalPerVertex='true' containerField='geometry'>
                        <Coordinate point='

EOT
    for (my $i=0; $i<$nt; $i++) {
        skip( $ELE );
        #<triangle #> <node> <node> <node> ... [attributes]
        my ($t,$n1,$n2,$n3) = split( /\s+/ );
    
        my ($x1,$y1) = split( /\s+/, $vertices[ $n1 ] );
        my ($x2,$y2) = split( /\s+/, $vertices[ $n2 ] );
        my ($x3,$y3) = split( /\s+/, $vertices[ $n3 ] );
    
        print "$x1 $y1 0 \n";
        print "$x2 $y2 0 \n";
        print "$x3 $y3 0 \n \n";
    
    }
    print << "EOT";
                        '/>
                    </TriangleSet>
                </shape>
EOT
}
#------------------------------------------------------------------------------
#output wire-frame/lines
sub outputWireFrame {
    for (my $i=0; $i<$nt; $i++) {
        skip( $ELE );
        #<triangle #> <node> <node> <node> ... [attributes]
        my ($t,$n1,$n2,$n3) = split( /\s+/ );
        my ($x1,$y1) = split( /\s+/, $vertices[ $n1 ] );
        my ($x2,$y2) = split( /\s+/, $vertices[ $n2 ] );
        my ($x3,$y3) = split( /\s+/, $vertices[ $n3 ] );

        print << "EOT";
            <Shape>
                <appearance> <material diffuseColor ='0.5 0.5 0.5' emissiveColor='0.0 0.0 0.0'></material> </appearance>
                <LineSet vertexCount="6"> <Coordinate point="
                    $x1 $y1 0    $x2 $y2 0
                    $x2 $y2 0    $x3 $y3 0
                    $x3 $y3 0    $x1 $y1 0
                    " /> </LineSet>
            </Shape>
EOT
    }
}
#------------------------------------------------------------------------------
#skip comment lines, blank lines, and leading spaces
sub skip {
    my $f = $_[0];
    while ( <$f> ) {
        # print STDERR $_;
        s/^\s+//;    #remove loading whitespace
        if ( !/^#/ && length($_) ) {
            last;
        }
    }
}
#------------------------------------------------------------------------------
sub header {
    print << "EOT";
<html>
    <head>
        <title>X3DOM page</title>
        <script type='text/javascript' src='http://www.x3dom.org/download/x3dom.js'> </script>
        <link rel='stylesheet' type='text/css' href='http://www.x3dom.org/download/x3dom.css'></link>
    </head>
    <body>
        <x3d width='1000px' height='800px'>
            <scene>
EOT
}
#------------------------------------------------------------------------------
sub footer {
    print << "EOT";
            </scene> 
        </x3d> 
        <h3> useful keys: </h3>
        <pre>
<!--
INFO: a: show all | d: show helper buffers | s: small feature culling | t: light view | m: toggle render mode | c: frustum culling | p: intersect type | r: reset view | 
e: examine mode | f: fly mode | y: freefly mode | w: walk mode | h: helicopter mode | l: lookAt mode | o: lookaround | g: game mode | n: turntable | u: upright position | 
v: print viewpoint info | pageUp: next view | pageDown: prev. view | +: increase speed | -: decrease speed 
-->
            a - show all
            c - frustum culling
            d - show help buffers
            e - examine mode
            f - fly mode
            g - game mode
            h - helicopter mode
            i - 
            l - look at mode
            m - render mode (vertices/line segments)
            n - turntable
            o - look around
            p - intersect type
            r - reset view
            s - small feature culling
            t - light view
            u - upright position
            v - print viewpoint info
            w - walk mode
            y - freefly mode
            + - increase speed
            - - decrease speed

            pageUp   - next view
            pageDown - prev view
        </pre>

        <p>
            <a href="http://www.x3dom.org/download/1.4/docs/singlehtml/#camera-navigation">camera navigation</a> <br/>
            <a href="http://doc.x3dom.org/tutorials/animationInteraction/navigation/index.html">navigation</a> <br/>
        </p>
        <h4> Generated by $0 by George J. Grevera, Ph.D. </h4>

    </body> 
</html>
EOT
}
#------------------------------------------------------------------------------
sub usage {
    print STDERR << "EOT";

usage: ele_node_to_x3dom.pl      {.ele_and_.node_file_prefix}
       ele_node_to_x3dom.pl  -v  {.ele_and_.node_file_prefix}
       for example:  The triangle program runs on a.poly and creates a.1.ele and a.1.node.
                     perl ele_node_to_x3dom_wire.pl a.1 > a.1.wire.html
       note:  can't use input redirection (<) because there are 2 input files (.ele and .node)!
EOT
    exit;
}
#------------------------------------------------------------------------------

