DROP TRIGGER IF EXISTS trigger_set_updated_at ON scraper_objects;
DROP FUNCTION IF EXISTs set_updated_at();

CREATE FUNCTION set_updated_at() RETURNS TRIGGER AS '
    BEGIN
        NEW.updated_at = now();
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS scraper_objects (
                                               website VARCHAR(255) NOT NULL
                                                   CONSTRAINT scraper_objects_pk PRIMARY KEY,
                                               url VARCHAR(255) NOT NULL
                                                   CONSTRAINT scraper_objects_unique UNIQUE,
                                               txtlocator VARCHAR NOT NULL,
                                               txthref VARCHAR NOT NULL,
                                               imglocator VARCHAR NOT NULL,
                                               imghref VARCHAR NOT NULL,
                                               title VARCHAR(255) NOT NULL,
                                               sum VARCHAR(255) NOT NULL,
                                               updated_at TIMESTAMP DEFAULT now()
);

DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_trigger
        WHERE tgname = ''trigger_set_updated_at''
    ) THEN
        CREATE TRIGGER trigger_set_updated_at
            BEFORE UPDATE ON scraper_objects
            FOR EACH ROW
        EXECUTE FUNCTION set_updated_at();
    END IF;
END;
';

INSERT INTO scraper_objects (
    website,
    url,
    txtlocator,
    txthref,
    imglocator,
    imghref,
    title,
    sum
)
VALUES
    ('aljazeera', 'https://www.aljazeera.com/africa/', 'section#news-feed-container h3 a', 'href', '.gc__image-wrap .responsive-image', 'src', 'h1', 'p.article__subhead'),
    ('sky', 'https://www.skynews.com.au/australia-news', 'h4 a', 'href', 'div.responsive-img', 'innerHTML', 'h1', '#story-intro'),
    ('france24', 'https://www.france24.com/en/europe', '.article__title a', 'href', '.m-item-list-article__wrapper picture', 'innerHTML', 'h1', '.t-content__chapo'),
    ('dpa', 'https://www.dpa-international.com/general-news/', '.card-content a', 'href', 'figure.image a img', 'src', 'h1', '.main'),
    ('ap', 'https://apnews.com/hub/latin-america', 'div.PagePromo-media a.Link[aria-label]', 'href', 'div.PagePromo-media a.Link[aria-label] picture[data-crop]', 'innerHTML', 'h1', '.RichTextStoryBody p'),
    ('allafrica', 'https://allafrica.com/', '.foundation a', 'href', '.img-responsive', 'src', '.topic h1', '.topic p'),
    ('aljazeera_middle_east', 'https://www.aljazeera.com/middle-east/', 'section#news-feed-container h3 a', 'href', '.gc__image-wrap .responsive-image', 'src', 'h1', 'p.article__subhead'),
    ('nrk', 'https://www.nrk.no/norge/', 'div.article.widget a', 'href', 'div.responsive-img', 'innerHTML', 'h1', 'div[data-main-content]'),
    ('agenciaBrasil', 'https://agenciabrasil.ebc.com.br/en/ultimas', 'a.capa-noticia', 'href', 'a.capa-noticia img', 'src', 'h1', '.conteudo-noticia p'),
    ('reuters_americas', 'https://www.reuters.com/world/americas/', '.story-card a[class*=''media-story-card'']', 'href', '.story-card .image', 'innerHTML', 'h1', 'div[data-testid^=''paragraph-0'']'),
    ('reuters', 'https://www.reuters.com/world/', '.story-card a[class*=''media-story-card'']', 'href', '.story-card .image', 'innerHTML', 'h1', 'div[data-testid^=''paragraph-0'']'),
    ('aap_pacific', 'https://aapnews.aap.com.au/topics/pacific-island-nations', 'div[itemprop=itemListElement] a', 'href', '.story-card a[class*=''media-story-card'']', 'innerHTML', 'h1', '.article-content p'),
    ('an', 'https://asianews.network/category/home-page/daily-digest/', 'div.post-img a', 'href', 'div.post-img a', 'innerHTML', 'h1.entry-title', 'div.entry-content'),
    ('diplomat_central_asia', 'https://thediplomat.com/regions/central-asia/', '.td-post', 'href', '.td-post', 'innerHTML', 'h1', '#td-lead')
ON CONFLICT (website) DO NOTHING;
