#pragma version(1)
#pragma rs java_package_name(com.allan.imgproc.oliver)
uchar4 *gIn;
uchar4 *gOut;
static const int kBlurWidth = 10;
void root(const ushort *v_in) 
{
  uchar4 *head = gIn + *v_in;
  float4 f4 = rsUnpackColor8888(*head); 
  float grey_val = (f4.r+f4.g+f4.b)/3.0; 
  f4.rgb = grey_val;
  *gOut = rsPackColorTo8888(f4);    
}