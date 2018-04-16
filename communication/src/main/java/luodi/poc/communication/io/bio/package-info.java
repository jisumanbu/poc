/**
 * Created by liujinjing on 2017/5/21.
 *
 * BIO的阻塞有两点：
 *  accept()    ->  resolve by setSoTimeout
 *  read()      ->  resolve by setSoTimeout
 *
 */
package luodi.poc.communication.io.bio;