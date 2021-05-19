arraydefault="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+,./;'[]<>?:=-{}\|~`1234567890¢¥±º×ßæŒÖû \""

# print(len(arraydefault))
array1=[0]*len(arraydefault)
array2=[0]*len(arraydefault)
array3=[0]*len(arraydefault)
array4=[0]*len(arraydefault)
array5=[0]*len(arraydefault)
array6=[0]*len(arraydefault)
array7=[0]*len(arraydefault)
array8=[0]*len(arraydefault)
array9=[0]*len(arraydefault)
array0=[0]*len(arraydefault)
array0=[0]*len(arraydefault)

array_of_array=[array0,array1,array2,array3,array4,array5,array6,array7,array8,array9]

shift0=8
shift1=12
shift2=24
shift3=36
shift4=48
shift5=60
shift6=72
shift7=84
shift8=96
shift9=103


#======================================================
def arraymaker(array1,array2,shift):
    shift=shift%len(arraydefault)
    for i in range(0,(len(array1)-shift)):
        array2[i]=array1[i+shift]
    for i in range(0,shift):
        array2[-shift+i]=array1[i]
   # for i in range (0,len(array2)):
   #     print(array2[i],end="")
   # print("\n")
1
#======================================================
def arraysmade(pwdshift):
    arraymaker(arraydefault,array1,shift1+pwdshift)
    arraymaker(arraydefault,array2,shift2+pwdshift)
    arraymaker(arraydefault,array3,shift3+pwdshift)
    arraymaker(arraydefault,array4,shift4+pwdshift)
    arraymaker(arraydefault,array5,shift5+pwdshift)
    arraymaker(arraydefault,array6,shift6+pwdshift)
    arraymaker(arraydefault,array7,shift7+pwdshift)
    arraymaker(arraydefault,array8,shift8+pwdshift)
    arraymaker(arraydefault,array9,shift9+pwdshift)
    arraymaker(arraydefault,array0,shift0+pwdshift)


    # for i in range (0,len(array_of_array)):
    #     for j in range (0,len(array_of_array[i])):
    #         print((array_of_array[i])[j],end="")
    #     print("\n")
#==================================================================
val='0'
while(val!='-1'):
    val=input("do u want to encrypt or decrypt (1 or 2) : ")

    if(val=='1'):
        text=input("enter a message you want to encrypt : ")
        pwd= input("enter password (only numbers) : ")
        arraysmade(int(pwd))
        encrypted=[]

        for i in range(0,len(text)):
            if(text[i] in arraydefault):
                damn=arraydefault.index(text[i])
                #print(damn)
            else:
                print("bruh")
            arr=array_of_array[int(pwd[i%len(pwd)])]
            #print(arr[damn],end="")
            encrypted.append(arr[damn])

        encryptedtext=''.join(map(str, encrypted))
        if(int(pwd)%2==0):
            encryptedtext=''.join(reversed(encryptedtext))
        print(encryptedtext)
        print("\n\n")
    #============================================================================
    elif(val=='2'):
        text=input("enter the encrypted message you want to decrypt : ")
        pwd= input("enter the password (only numbers) : ")
        arraysmade(int(pwd))
        decrypted=[]
        if(int(pwd)%2==0):
            text=''.join(reversed(text))
        for i in range(0,len(text)):
            if(text[i] in array_of_array[int(pwd[i%len(pwd)])]):
                index=array_of_array[int(pwd[i%len(pwd)])].index(text[i])
                decrypted.append(arraydefault[index])
        decryptedtext=''.join(map(str, decrypted))
        print(decryptedtext)
        print("\n\n")

    #===========================================================================
    else:
        print("bruh")
    val=input("Again? Enter -1 to exit ")
