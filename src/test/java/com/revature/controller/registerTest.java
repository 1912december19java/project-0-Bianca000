package com.revature.controller;

import static org.junit.Assert.*;
import org.junit.Test;


public class registerTest {

  @Test
  public void test() {
    bankingSystem bnk = new bankingSystem();
    String uname = null;
    String upass = null;
    boolean flag = bnk.Registration(uname, upass);
    assertEquals(true, flag);
  }

}
