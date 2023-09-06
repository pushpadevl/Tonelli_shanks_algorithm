import java.util.*;
public class Tonelli_Shanks {
    /* PROBLEM Statement
     * Given a in Zp, find b | b^2 = amod Zp
     * 
     * Functions needed:
     *      1. isQR() -> Legendre symbol Quadratic residue check
     *      2. binaryExp() -> calculating a power x
     *      3. findRootmodP)() -> find b | b^2=a mod P
     *      4. modInv() -> Find modulo inverse of a in Zp
     *      5. gcd() -> Find greatest common divisor of a,b
     *      6. CRT() -> Chinese remainder theorem     
     */
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        while(true){
            /*
             * 
             System.out.println("Enter prime P, and number n.");
             long P = sc.nextLong();
             long n= sc.nextLong();
             long root = findRootmodP(n%P,P);
             System.out.println(modInv(n,P));
             */
            System.out.println("Enter P, Q");
            long P = sc.nextLong();
            long Q = sc.nextLong();
            
            System.out.println("Enter rp, rq,");
            while(true){

                long rp = sc.nextLong();
                long rq = sc.nextLong();
                System.out.println(CRT(rp, rq, P, Q, P*Q));
            }
            
        }
        
    }
    static long CRT(long rp, long rq, long P, long Q, long PQ){ //n=pq, chinese remainder theorem for two prime composite N
        long res=0;
        res = (PQ+(rp*(modInv(Q,P))*Q + rq*P*modInv(P,Q))%PQ)%PQ;
        return res; 
    }
    static long gcd(long a, long b){ //euclidean algorithm
        if(b==0) return a;
        else return gcd(b,a%b);
    }
    static long modInv(long a, long P){ //p -> prime, find a inverse using exteneded euclidean algorithm
        if(gcd(a,P)!=1) {
            System.out.println(a+" and "+P+" are not coprimes");
            return -1L;
        }
        int i=0;
        long r[]=new long[3];
        long x[]=new long[3];
        long y[]=new long[3];
        long q=0;
        r[0] = a;
        r[1] = P;
        x[0] = 1L;
        y[0] = 0L;
        x[1] = 0L;
        y[1] = 1L;

        while(r[1] != 1L){
            r[2] = r[0]%r[1];
            q = r[0]/r[1];
            x[2] = x[0] - q*x[1];
            y[2] = y[0] - q*y[1];
          //  System.out.printf("r=%d q=%d, x=%d y=%d\n",r[2],q,x[2],y[2]);

            r[0] = r[1]; r[1] = r[2];
            x[0] = x[1]; x[1] = x[2];
            y[0] = y[1]; y[1] = y[2];
        }

        return x[1]%P;

    }
    static long findRootmodP(long n, long P){ // // given a in Zp, find b | b^2 = amod Zp
        if(!isQR(n,P)){
            System.out.println(n+" is not a QR mod "+P);
            return -1;
        }
        long S=0, R=0, M=0,C=0,T=0, B=0;
        long Q = P-1;
        while((Q & 0x1)==0){
            Q>>=1;
            S++;
        }
        for(int z=2;z<P;z++){
            if(isQR(z,P)){continue;}
            else {
                C = binaryExp(z, Q) %P;
                break;
            }
        }
        //setting init values
        M=S;
        //C already set above
        T=binaryExp(n, Q)%P;
        R=binaryExp(n, (Q+1)/2) %P;
        
          System.out.printf("T=%d B=%d M=%d C=%d R=%d \n",T,B,M,C,R);
        while(T>1){
            long i=0;
            long TT=T;
            while(TT!=1){
                TT = binaryExp(TT, 1<<(++i)) %P ;
            }
            B = binaryExp(C, 1<<(M-i-1)) %P;
            M=i;
            C = (B*B)%P;
            T = (T*C)%P;  
            R = (R*B)%P;
            System.out.printf("T=%d i=%d B=%d M=%d C=%d R=%d \n",T,i,B,M,C,R);
        }
             return R;
    
}
    static boolean isQR(long n, long P){ // is n a Quadratice Residue modulo prime P
        long Pm1b2 = (P-1)/2;
        long legNP = binaryExp(n, Pm1b2) % P;

        if(legNP==1) return true;
        else return false;
    }
    static long binaryExp(long a, long x){// calc a^x
    String arr = Long.toBinaryString(x);
    long ax=1;
    for(int i=0;i<arr.length();i++){
        if(arr.charAt(i)=='1'){
            ax*=ax;
            ax*=a;
        }else{
            ax*=ax;
        }
    }
   // System.out.println(ax);
    return ax;
}
}

