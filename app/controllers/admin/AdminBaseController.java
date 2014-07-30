package controllers.admin;

import controllers.CRUD;
import controllers.securesocial.SecureSocial;
import play.mvc.With;

/**
 * User: daniel.schoonmaker
 * Date: 3/13/14
 * Time: 5:36 PM
 */
@With(SecureSocial.class)
public class AdminBaseController extends CRUD {

}