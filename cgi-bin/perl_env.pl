#!D:\StrawberryPerl\perl\bin\perl.exe -T
use strict;
use CGI qw(:standard);
use CGI::Carp qw(warningsToBrowser fatalsToBrowser);
print header;
print start_html("Environment");
foreach my $key (sort(keys(%ENV))) {
    print "$key = $ENV{$key}<br>\n";
}

print end_html;
