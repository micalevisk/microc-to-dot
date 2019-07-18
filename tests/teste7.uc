void main()
{
    int i, n;
    scanf(n); 
    i = 0;
    while (i < n) {
        if (reached_timeout() == 1)
             return;
        i = i + 1;
    }
    printf("done!");
}
