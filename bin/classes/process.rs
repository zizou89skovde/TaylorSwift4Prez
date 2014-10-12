#pragma version(1)
#pragma rs java_package_name(com.allan.imgproc.oliver)

void root(const uchar4 *v_in, uchar4 *v_out) 
{
  float4 f4 = rsUnpackColor8888(*v_in);
  float gray_value = (f4.r + f4.b +f4.g)/3.0;
  f4.r = gray_value;
  f4.g = gray_value; 
  f4.b = gray_value; 
  *v_out = rsPackColorTo8888(f4);   
}