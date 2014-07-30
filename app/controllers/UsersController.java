package controllers;

import controllers.securesocial.SecureSocial;
import play.mvc.*;

/**
 * User: daniel.schoonmaker
 * Date: 3/13/14
 * Time: 7:32 PM
 */
@With(SecureSocial.class)
public class UsersController extends CRUD {

}
