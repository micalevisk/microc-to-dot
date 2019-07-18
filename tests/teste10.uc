void main() {
    int a, b, c;
    a = 0;
    b = 0;
    while (b < 10) {
        if (b % 2 == 0) {
            scanf(a);
        }
        else
            a = a - 1;
        b = b + 1;
    }
    b = 1;
    c = 2;
    for (a = 0; a < 10 - c; a = a + b) {
        if (a % 2 == 0)
            printf(a);
        else
            return 0;
        b = b + 2;
    }
}
