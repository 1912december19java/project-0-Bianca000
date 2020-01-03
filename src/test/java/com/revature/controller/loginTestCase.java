package com.revature.controller;

import static org.junit.Assert.*;
import org.junit.Test;

public class loginTestCase {

  @Test
  public void test() {
    bankingSystem bank = new bankingSystem();
    boolean flag = bank.login();
    assertEquals(true, flag); //I want true
  }

}
