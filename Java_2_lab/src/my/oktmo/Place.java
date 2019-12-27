package my.oktmo;

public class Place{
  private long code;
  private String status;
  private String name;

  Place(Long codearg, String namearg, String statusarg){
    this.code = codearg;
    this.name = namearg;
    this.status = statusarg;
  }

  public String getStatus(){return this.status;}
  public String getName(){
    return this.name;
  }

  public Long getCode(){
    return this.code;
  }
  public String toString(){
    return (this.code + " " + this.name + " " + this.status);
  }
}
