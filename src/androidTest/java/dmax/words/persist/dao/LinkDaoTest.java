package dmax.words.persist.dao;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Iterator;

import dmax.words.domain.Language;
import dmax.words.domain.Link;
import dmax.words.domain.Word;
import dmax.words.persist.Dao;
import dmax.words.persist.DataBaseManager;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 16.12.14 at 18:08
 */
public class LinkDaoTest extends AndroidTestCase {

    private DataBaseManager db;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        RenamingDelegatingContext newContext = new RenamingDelegatingContext(getContext(), "test_");
        db = new DataBaseManager(newContext);
        db.open();
    }

    public void test_shouldReturnCorrectDao() {
        Dao<Link> dao = DaoFactory.createDao(Link.class);

        assertEquals(LinkDao.class, dao.getClass());
    }

    public void test_shouldSave() {
        Word w = new Word();
        w.setLanguage(Language.POLISH);
        w.setData("mówię");

        Word w1 = new Word();
        w1.setLanguage(Language.UKRAINIAN);
        w1.setData("укр");

        Dao<Word> dao = DaoFactory.createDao(Word.class);
        Word saved = db.save(dao.setPersistable(w));
        Word saved2 = db.save(dao.setPersistable(w1));

        Link link = new Link();
        link.setWordId(Language.UKRAINIAN, saved.getId());
        link.setWordId(Language.POLISH, saved2.getId());

        Dao<Link> dao1 = DaoFactory.createDao(Link.class);
        Link saved3 = db.save(dao1.setPersistable(link));

        assertFalse(saved.getId() == -1);
        assertTrue(link.equals(saved3));
    }

    public void test_shouldRetrieve() {
        Word w = new Word();
        w.setLanguage(Language.POLISH);
        w.setData("mówię");

        Word w1 = new Word();
        w1.setLanguage(Language.UKRAINIAN);
        w1.setData("укр");

        Dao<Word> dao = DaoFactory.createDao(Word.class);
        Word saved = db.save(dao.setPersistable(w));
        Word saved2 = db.save(dao.setPersistable(w1));

        Link link = new Link();
        link.setWordId(Language.UKRAINIAN, saved.getId());
        link.setWordId(Language.POLISH, saved2.getId());

        Dao<Link> dao1 = DaoFactory.createDao(Link.class);
        Link saved3 = db.save(dao1.setPersistable(link));
        long id = saved3.getId();

        Link retrieved = db.retrieve(dao1.setRetrieveId(id));

        assertNotNull(retrieved);
        assertTrue(retrieved.getId() == saved.getId());
        assertTrue(retrieved.equals(saved3));
    }

    public void test_shouldDelete() {
        Word w = new Word();
        w.setLanguage(Language.POLISH);
        w.setData("mówię");

        Word w1 = new Word();
        w1.setLanguage(Language.UKRAINIAN);
        w1.setData("укр");

        Dao<Word> dao = DaoFactory.createDao(Word.class);
        Word saved = db.save(dao.setPersistable(w));
        Word saved2 = db.save(dao.setPersistable(w1));

        Link link = new Link();
        link.setWordId(Language.UKRAINIAN, saved.getId());
        link.setWordId(Language.POLISH, saved2.getId());

        Dao<Link> dao1 = DaoFactory.createDao(Link.class);
        Link saved3 = db.save(dao1.setPersistable(link));
        long id = saved3.getId();
        boolean deleteResult = db.delete(dao1.setRetrieveId(id));
        Link retrieved = db.retrieve(dao1.setRetrieveId(id));

        assertTrue(deleteResult);
        assertNull(retrieved);
    }


    public void test_shouldRetrieveIterator() {
        Word w = new Word();
        w.setLanguage(Language.UKRAINIAN);
        w.setData("укр");

        Word w2 = new Word();
        w2.setLanguage(Language.UKRAINIAN);
        w2.setData("горілка");

        Word w3 = new Word();
        w3.setLanguage(Language.POLISH);
        w3.setData("mówię");

        Word w4 = new Word();
        w4.setLanguage(Language.POLISH);
        w4.setData("rozumiem");

        Dao<Word> dao = DaoFactory.createDao(Word.class);
        long id1 = db.save(dao.setPersistable(w)).getId();
        long id2 = db.save(dao.setPersistable(w2)).getId();
        long id3 = db.save(dao.setPersistable(w3)).getId();
        long id4 = db.save(dao.setPersistable(w4)).getId();

        Dao<Link> dao1 = DaoFactory.createDao(Link.class);
        Link link = new Link();
        link.setWordId(Language.UKRAINIAN, id1);
        link.setWordId(Language.POLISH, id3);
        Link link2 = new Link();
        link2.setWordId(Language.UKRAINIAN, id2);
        link2.setWordId(Language.POLISH, id4);

        assertNotNull(db.save(dao1.setPersistable(link)));
        assertNotNull(db.save(dao1.setPersistable(link2)));

        Iterator<Link> it = db.retrieveIterator(dao1);

        assertNotNull(it);
        assertTrue(it.hasNext()); // first
        assertNotNull(it.next());
        assertTrue(it.hasNext()); // second
        assertNotNull(it.next());
        assertFalse(it.hasNext()); // no else
    }
}